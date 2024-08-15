package com.ottistech.indespensa.api.ms_indespensa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hello-world")
public class HelloWorldController {
    @GetMapping("hello")
    public String hello() {
        return "Hello World!";
    }
}
