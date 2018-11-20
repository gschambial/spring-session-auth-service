package com.unsaidtalks.auth.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.unsaidtalks.auth.AuthServiceApp;
/**
 * Test class for the LoginSuccessResource REST controller.
 *
 * @see LoginSuccessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthServiceApp.class)
public class LoginSuccessResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        LoginSuccessResource loginSuccessResource = new LoginSuccessResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(loginSuccessResource)
            .build();
    }

    /**
     * Test loginSuccess
     */
    @Test
    public void testLoginSuccess() throws Exception {
        restMockMvc.perform(get("/success"))
            .andExpect(status().isOk());
    }
}
