package com.it.security.controller;

import com.it.security.common.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class helloController {
    @GetMapping
    @PreAuthorize("@leafEx.hasAuthority('system:select:list')")
    public String hello() {
        return "h";
    }

    @PostMapping("/testCors")
    public ResponseResult testCors() {
        return new ResponseResult(200, "testCors");
    }

}
