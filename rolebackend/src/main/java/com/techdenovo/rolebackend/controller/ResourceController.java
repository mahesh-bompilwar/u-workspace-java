package com.techdenovo.rolebackend.controller;

import com.techdenovo.rolebackend.config.CustomUserDetailsService;
import com.techdenovo.rolebackend.model.ResourceRequest;
import com.techdenovo.rolebackend.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class ResourceController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/hiadmin")
    public String welcomeadmin(){
        return  "hello admin";
    }

    @GetMapping("/hiuser")
    public String welcomeuser(){
        return  "hellouser";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/getUser")
    public ResponseEntity<?> getUser(@RequestBody ResourceRequest resourceRequest){

        System.out.println("-----------in getUser");
        return ResponseEntity.ok(userDetailsService.getUser(resourceRequest));
    }
}
