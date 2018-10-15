package com.imooc.example.springdtx.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mavlarn on 2018/1/17.
 */
@RestController
@RequestMapping("/api")
public class HomeResource {

    @RequestMapping("/hello")
    @Secured("ROLE_USER")
    public String hello() {
        return "world";
    }
}
