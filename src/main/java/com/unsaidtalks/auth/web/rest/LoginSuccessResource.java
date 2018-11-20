package com.unsaidtalks.auth.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginSuccessResource controller
 */
@RestController
public class LoginSuccessResource {

    private final Logger log = LoggerFactory.getLogger(LoginSuccessResource.class);

    /**
    * GET loginSuccess
    */
    @GetMapping("/success")
    public String loginSuccess() {
    	log.info("*****************Redirecting after successful login************************");
        return "loginSuccess";
    }

}
