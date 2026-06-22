package com.procureflow.procureflowbackend.common;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /*@GetMapping("/api/test")
    public String test(Authentication authentication) {

        return "Hello "
                + authentication.getName()
                + ", JWT Authentication Working!";
    }*/

    @GetMapping("/api/test")
    public Object test(Authentication authentication) {

        return authentication.getAuthorities();
    }
}
