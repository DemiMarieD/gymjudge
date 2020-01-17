package com.ase.gymjudge.serviceTesting;

import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.UserRepository;
import com.ase.gymjudge.services.UserService;
import com.ase.gymjudge.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceImplIntegrationTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Autowired
        private UserService userService;

        @MockBean
        private UserRepository userRepository;

        // write test cases here
        @Before
        public void setUp() {
            User alex = new User();
            alex.setFirstname("alex");
            alex.setEmail("alex@gmail.com");

            Mockito.when(userRepository.findByEmail(alex.getEmail()))
                    .thenReturn(alex);
        }

        @Test
        public void whenValidName_thenEmployeeShouldBeFound() {
            String name = "alex@gmail.com";
            User found = userService.findByEmail(name);

            assertThat(found.getEmail())
                    .isEqualTo(name);
        }
    }

}
