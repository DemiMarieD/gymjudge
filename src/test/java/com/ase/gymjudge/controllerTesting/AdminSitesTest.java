package com.ase.gymjudge.controllerTesting;


import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.UserRepository;
import com.ase.gymjudge.services.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeSuite;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//todo only working with jUnit4...
@RunWith(SpringRunner.class) //need this for the webApplication context
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
public class AdminSitesTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private MockMvc mvc;

    //todo only working with @before (JUnit4) (no ..Each/All/Class/Suite)
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        //needs to be after mvc init.
        User alex = new User();
        alex.setFirstname("alex");
        alex.setEmail("alex@web.at");

        Mockito.when(userService.findByEmail(alex.getEmail()))
                .thenReturn(alex);

    }

    @Test
    public void signUp_Get() throws Exception {
        mvc
                .perform(get("/signup"))
                .andExpect(status().isOk());
    }

    @Test
    public void signUp_Post() throws Exception {
        mvc
            .perform(post("/signup"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "alex@web.at", authorities = "ADMIN")
    public void Login_WithUser() throws Exception {
        mvc
                .perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "alex@web.at", authorities = "ADMIN")
    public void requestHomeUrl_WithUser() throws Exception {
        mvc
                .perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void requestHomeUrl_WithoutUser() throws Exception {
        mvc
                .perform(get("/home"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

}
