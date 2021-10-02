package com.project.backend.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @ApiOperation(value = "User login.")
    @PostMapping
    public String auth(){
        log.info("Controller: Request to user login");
        return "You are authenticated";
    }
}
