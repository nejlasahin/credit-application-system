package com.project.backend.controller;

import com.project.backend.dto.ApplicationDto;
import com.project.backend.dto.CustomerDto;
import com.project.backend.enums.CreditResult;
import com.project.backend.service.ApplicationService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @Mock
    ApplicationService applicationService;

    @InjectMocks
    ApplicationController applicationController;

    @Test
    public void getAll(){
        ApplicationDto applicationDto1 = ApplicationDto.builder().id(1l).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(new CustomerDto()).build();
        ApplicationDto applicationDto2 = ApplicationDto.builder().id(2l).creditLimit(new BigDecimal("10000")).creditResult(CreditResult.CONFIRMED).customer(new CustomerDto()).build();
        ApplicationDto applicationDto3 = ApplicationDto.builder().id(3l).creditLimit(new BigDecimal("0")).creditResult(CreditResult.UNCONFIRMED).customer(new CustomerDto()).build();

        List<ApplicationDto> applicationDtos = Arrays.asList(applicationDto1, applicationDto2, applicationDto3);

        when(applicationService.getAll()).thenReturn(applicationDtos);

        ResponseEntity<List<ApplicationDto>> actual = applicationController.getAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getStatusCode(), HttpStatus.OK),
                () -> assertEquals(actual.getBody().size(), applicationDtos.size())
        );

    }

    @Test
    public void getStatus(){
        ApplicationDto applicationDto1 = ApplicationDto.builder().id(1l).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(new CustomerDto()).build();

        when(applicationService.getStatus(anyString())).thenReturn(applicationDto1);

        ResponseEntity<ApplicationDto> actual = applicationController.getStatus(anyString());

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getStatusCode(), HttpStatus.OK),
                () -> assertEquals(actual.getBody().getCustomer(), applicationDto1.getCustomer())
        );

    }
}