package com.project.backend.controller;

import com.project.backend.dto.CustomerDto;
import com.project.backend.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Create customer.")
    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody @Valid CustomerDto customerDto){
        log.info("Controller: Request to save customer with object information");
        return new ResponseEntity<>(customerService.save(customerDto), HttpStatus.OK);
    }

    @ApiOperation(value = "List all customers.")
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(){
        log.info("Controller: Request to list all customers");
        return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get customer by id.")
    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerDto> get(@PathVariable("id") long id){
        log.info("Controller: Request to fetch customer with id information");
        return new ResponseEntity<>(customerService.get(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Update customer.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@RequestBody @Valid CustomerDto customerDto, @PathVariable("id") long id){
        customerDto.setId(id);
        log.info("Controller: Request to update customer with object information");
        return new ResponseEntity<>(customerService.update(customerDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete customer.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id){
        log.info("Controller: Request to delete customer with id information");
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
