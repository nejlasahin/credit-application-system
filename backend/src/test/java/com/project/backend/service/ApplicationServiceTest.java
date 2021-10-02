package com.project.backend.service;

import com.project.backend.dto.ApplicationDto;
import com.project.backend.dto.CustomerDto;
import com.project.backend.enums.CreditResult;
import com.project.backend.mapper.ApplicationMapper;
import com.project.backend.model.Application;
import com.project.backend.model.Customer;
import com.project.backend.repository.ApplicationRepository;
import com.project.backend.service.impl.ApplicationServiceImpl;
import com.project.backend.util.notificationService.NotificationService;
import com.project.backend.util.scoreService.ScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    ApplicationMapper applicationMapper;

    @Mock
    NotificationService notificationService;

    @Mock
    ScoreService scoreService;

    @InjectMocks
    ApplicationServiceImpl applicationService;


    @Test
    public void makeApplication(){

        Customer customer = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();

        Application application = Application.builder().creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(customer).build();
        ApplicationDto applicationDto = ApplicationDto.builder().creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(customerDto).build();

        when(scoreService.getScore(customer.getIdentityNumber())).thenReturn(550);
        when(applicationRepository.save(application)).thenReturn(application);
        when(notificationService.sendSms(customer.getPhoneNumber(), Boolean.TRUE)).thenReturn(Boolean.TRUE);
        when(applicationMapper.mapFromApplicationToApplicationDto(application)).thenReturn(applicationDto);

        ApplicationDto actual = this.applicationService.makeApplication(customer);


        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getCreditResult(), applicationDto.getCreditResult()),
                () -> assertEquals(actual.getCreditLimit(), applicationDto.getCreditLimit()),
                () -> assertEquals(actual.getCustomer(), applicationDto.getCustomer())
        );

    }

    @Test
    public void update(){

        Customer customer = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();

        Application application = Application.builder().id(1L).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(customer).build();
        ApplicationDto applicationDto = ApplicationDto.builder().id(1l).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(customerDto).build();

        when(scoreService.getScore(customer.getIdentityNumber())).thenReturn(550);
        when(applicationRepository.save(application)).thenReturn(application);
        when(notificationService.sendSms(customer.getPhoneNumber(), Boolean.TRUE)).thenReturn(Boolean.TRUE);
        when(applicationMapper.mapFromApplicationToApplicationDto(application)).thenReturn(applicationDto);

        ApplicationDto actual = this.applicationService.update(customer, 1L);


        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getCreditResult(), applicationDto.getCreditResult()),
                () -> assertEquals(actual.getCreditLimit(), applicationDto.getCreditLimit()),
                () -> assertEquals(actual.getCustomer(), applicationDto.getCustomer())
        );

    }

    @Test
    public void getStatus(){

        Customer customer = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();

        Application application = Application.builder().id(1L).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(customer).build();
        ApplicationDto applicationDto = ApplicationDto.builder().id(1l).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(customerDto).build();

        when(applicationRepository.findByCustomerIdentityNumber(customer.getIdentityNumber())).thenReturn(application);
        when(applicationMapper.mapFromApplicationToApplicationDto(application)).thenReturn(applicationDto);

        ApplicationDto actual = this.applicationService.getStatus("11111111112");

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getCustomer(), applicationDto.getCustomer()),
                () -> assertEquals(actual, applicationDto)
        );

    }

    @Test
    public void getAll(){

        Application application1 = Application.builder().id(1L).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(new Customer()).build();
        Application application2 = Application.builder().id(2L).creditLimit(new BigDecimal("10000")).creditResult(CreditResult.CONFIRMED).customer(new Customer()).build();
        Application application3 = Application.builder().id(3L).creditLimit(new BigDecimal("0")).creditResult(CreditResult.UNCONFIRMED).customer(new Customer()).build();

        ApplicationDto applicationDto1 = ApplicationDto.builder().id(1l).creditLimit(new BigDecimal("20000")).creditResult(CreditResult.CONFIRMED).customer(new CustomerDto()).build();
        ApplicationDto applicationDto2 = ApplicationDto.builder().id(2l).creditLimit(new BigDecimal("10000")).creditResult(CreditResult.CONFIRMED).customer(new CustomerDto()).build();
        ApplicationDto applicationDto3 = ApplicationDto.builder().id(3l).creditLimit(new BigDecimal("0")).creditResult(CreditResult.UNCONFIRMED).customer(new CustomerDto()).build();

        List<Application> applications = Arrays.asList(application1, application2, application3);
        List<ApplicationDto> applicationDtos = Arrays.asList(applicationDto1, applicationDto2, applicationDto3);

        when(applicationRepository.findAll()).thenReturn(applications);
        when(applicationMapper.mapFromApplicationsToApplicationDto(applications)).thenReturn(applicationDtos);

        List<ApplicationDto> actual = this.applicationService.getAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual, applicationDtos),
                () -> assertEquals(actual.size(), applicationDtos.size())
        );

    }


}
