package com.ase.gymjudge.configuration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;


    private final String USERS_QUERY = "select email, password, active from user where email=?";
    private final String ROLES_QUERY = "select u.email, r.role from user u inner join user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id=r.role_id) where u.email=?";
    private final String JUDGE_QUERY = "select login, password from judge where login=?";
    private final String JUDGEROLE_QUERY = "select j.login, r.role from judge j inner join role r on (j.role_role_id = r.role_id) where login=?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                // .usersByUsernameQuery(JUDGE_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                // .authoritiesByUsernameQuery(JUDGEROLE_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/livescores/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/home/**").hasAuthority("ADMIN")
                .antMatchers("/roundsoverview/**").hasAuthority("JUDGE")
                .anyRequest().authenticated()
                .and().csrf().disable()
                //forAdmin
                .formLogin().loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/login_successful")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                //forJudge
                /*
                .formLogin().loginPage("/judge/login")
                .failureUrl("/judge/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("login")
                .passwordParameter("password")
                .and()
                 */
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60)
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");
    }
//
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);

        return db;
    }
}