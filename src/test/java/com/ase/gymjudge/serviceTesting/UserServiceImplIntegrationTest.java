package com.ase.gymjudge.serviceTesting;

import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.RoleRepository;
import com.ase.gymjudge.repositories.UserRepository;
import com.ase.gymjudge.services.UserService;
import com.ase.gymjudge.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


@DataJpaTest
public class UserServiceImplIntegrationTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration{
        @Bean
        public UserService userService(){
            return new UserServiceImpl();
        }
    }
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        Role judgeRole = new Role();
        judgeRole.setRole("JUDGE");
        Mockito.when(roleRepository.findByRole(judgeRole.getRole()))
                .thenReturn(judgeRole);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        Mockito.when(roleRepository.findByRole(adminRole.getRole()))
                .thenReturn(adminRole);
    }

  /*  @Test
    public void saveJudgeTest() {
        User testUser = new User();
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("123");
        userService.saveJudge(testUser);

        Assert.assertEquals(roleRepository.findByRole("JUDGE"), userRepository.findByEmail("test@gmail.com").getRoles().getRole());
    }
        */

}
