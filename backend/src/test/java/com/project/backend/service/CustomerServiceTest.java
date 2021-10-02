package com.project.backend.service;

import com.project.backend.dto.CustomerDto;
import com.project.backend.mapper.CustomerMapper;
import com.project.backend.model.Customer;
import com.project.backend.repository.CustomerRepository;
import com.project.backend.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;

    @Mock
    ApplicationService applicationService;

    @InjectMocks
    CustomerServiceImpl customerService;


    @Test
    public void save(){

        Customer customer = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();


        when(customerMapper.mapFromCustomerDtoToCustomer(customerDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.mapFromCustomerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto actual = this.customerService.save(customerDto);


        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getFirstName(), customerDto.getFirstName()),
                () -> assertEquals(actual, customerDto),
                () -> assertEquals(actual.getId(), customerDto.getId())
        );

    }

    @Test
    public void getAll(){

        Customer customer1 = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        Customer customer2 = Customer.builder().id(1L).identityNumber("11111111156").firstName("First Name 2").lastName("Last Name 2").salary(new BigDecimal("9115")).phoneNumber("7774443322").build();
        Customer customer3 = Customer.builder().id(1L).identityNumber("11111111178").firstName("First Name 3").lastName("Last Name 3").salary(new BigDecimal("3415")).phoneNumber("6664443322").build();

        CustomerDto customerDto1 = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto2 = CustomerDto.builder().id(1L).identityNumber("11111111156").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("9115")).phoneNumber("7774443322").build();
        CustomerDto customerDto3 = CustomerDto.builder().id(1L).identityNumber("11111111178").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("3415")).phoneNumber("6664443322").build();


        List<Customer> customers = Arrays.asList(customer1, customer2, customer3);
        List<CustomerDto> customerDtos = Arrays.asList(customerDto1, customerDto2, customerDto3);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.mapFromCustomersToCustomerDto(customers)).thenReturn(customerDtos);

        List<CustomerDto> actual = this.customerService.getAll();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual, customerDtos),
                () -> assertEquals(actual.size(), customerDtos.size())
        );

    }

    @Test
    public void get(){

        Customer customer = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();

        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerMapper.mapFromCustomerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto actual = this.customerService.get(1L);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual, customerDto),
                () -> assertEquals(actual.getId(), customerDto.getId())
        );

    }

    @Test
    public void update(){

        Customer customer = Customer.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();
        CustomerDto customerDto = CustomerDto.builder().id(1L).identityNumber("11111111112").firstName("First Name 1").lastName("Last Name 1").salary(new BigDecimal("5115")).phoneNumber("5554443322").build();


        when(customerMapper.mapFromCustomerDtoToCustomer(customerDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.mapFromCustomerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto actual = this.customerService.save(customerDto);


        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(actual.getFirstName(), customerDto.getFirstName()),
                () -> assertEquals(actual, customerDto),
                () -> assertEquals(actual.getId(), customerDto.getId())
        );

    }
}
