package com.project.backend.controller;

import com.project.backend.dto.CustomerDto;
import com.project.backend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    @Test
    public void save(){
        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();

        when(customerService.save(customerDto)).thenReturn(customerDto);

        ResponseEntity<CustomerDto> actual = customerController.save(customerDto);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getStatusCode(), HttpStatus.OK),
                () -> assertEquals(actual.getBody().getFirstName(), customerDto.getFirstName())
        );

    }

    @Test
    public void getAll(){
        CustomerDto customerDto1 = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto2 = CustomerDto.builder().id(1L).identityNumber("11111111156").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("9115")).phoneNumber("7774443322").build();
        CustomerDto customerDto3 = CustomerDto.builder().id(1L).identityNumber("11111111178").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("3415")).phoneNumber("6664443322").build();

        List<CustomerDto> customerDtos = Arrays.asList(customerDto1, customerDto2, customerDto3);

        when(customerService.getAll()).thenReturn(customerDtos);

        ResponseEntity<List<CustomerDto>> actual = customerController.getAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getStatusCode(), HttpStatus.OK),
                () -> assertEquals(actual.getBody().size(), customerDtos.size())
        );

    }

}