package com.project.backend.controller;

import com.project.backend.dto.ApplicationDto;
import com.project.backend.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @ApiOperation(value = "List all applications.")
    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAll(){
        log.info("Controller: Request to list all applications");
        return new ResponseEntity<>(applicationService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get application by identity number.")
    @GetMapping("/get-status/{identiyNumber}")
    public ResponseEntity<ApplicationDto> getStatus(@PathVariable("identiyNumber") String identiyNumber){
        log.info("Controller: Request to fetch application with identity number information");
        return new ResponseEntity<>(applicationService.getStatus(identiyNumber), HttpStatus.OK);
    }


}
