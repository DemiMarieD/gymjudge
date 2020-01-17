package com.ase.gymjudge;

import com.ase.gymjudge.repositories.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

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
}
