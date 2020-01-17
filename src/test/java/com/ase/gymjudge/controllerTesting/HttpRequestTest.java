package com.ase.gymjudge.controllerTesting;

import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.RoleRepository;
import com.ase.gymjudge.repositories.UserRepository;
import com.ase.gymjudge.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
   @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void homepageTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Login");
    }

    @Test
    public void userNotLoggedIn() {
        // User should not be able to access competition overview
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/home/competitions",
                String.class)).contains("Sign in");
        // User should not be able to access judges overview
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/roundsoverview",
                String.class)).contains("Sign in");
        // User should always be redirected to sign form if url is not valid
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/anything",
                String.class)).contains("Sign in");
        // 404 Error should only occur if an entity is not found
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/livescores",
                String.class)).contains("Not Found");
    }


    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");

        User alex = new User();
        alex.setFirstname("alex");
        alex.setEmail("alex@gmail.com");
        alex.setRoles(adminRole);
        alex.setPassword(bCryptPasswordEncoder.encode("1234"));

        Mockito.when(userRepository.findByEmail(alex.getEmail()))
                .thenReturn(alex);

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    //selenium akzeptanz test besser

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        RequestBuilder requestBuilder = formLogin().user("alex@gmail.com").password("1234");
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isFound());
              //  .andExpect(cookie().exists("JSESSIONID"));
      /*  mvc.perform(get("/home").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());*/
    }

    @Test
    @WithMockUser(username = "alex@gmail.com", password = "1234", roles = "ADMIN")
    public void loginTest() throws Exception {
        // Admin should be able to access competition overview
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/home/competitions",
                String.class)).contains("Sign out");
    }
}
