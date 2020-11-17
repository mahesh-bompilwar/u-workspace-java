package com.techdenovo.rolebackend.controller;

import com.techdenovo.rolebackend.config.CustomUserDetailsService;
import com.techdenovo.rolebackend.model.DAOUser;
import com.techdenovo.rolebackend.model.ResourceRequest;
import com.techdenovo.rolebackend.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(value = "/user")
    public ResponseEntity<?> getUser(@RequestParam String username){

        System.out.println("-----------in getUser");
        return ResponseEntity.ok(userDetailsService.getUser(username));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/users")
    public ResponseEntity<List<DAOUser>> getUsers(){
        System.out.println("-----------in getUsers");
        return ResponseEntity.ok(userDetailsService.getUsers());
    }
}
