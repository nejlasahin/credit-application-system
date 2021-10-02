package com.project.backend.service.impl;

import com.project.backend.dto.CustomerDto;
import com.project.backend.exceptions.IdentityNumberIsAlreadyExistException;
import com.project.backend.exceptions.PhoneNumberIsAlreadyExistException;
import com.project.backend.exceptions.ResourceNotFoundException;
import com.project.backend.mapper.CustomerMapper;
import com.project.backend.model.Customer;
import com.project.backend.repository.CustomerRepository;
import com.project.backend.service.ApplicationService;
import com.project.backend.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ApplicationService applicationService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, ApplicationService applicationService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.applicationService = applicationService;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        if(customerRepository.existsByIdentityNumber(customerDto.getIdentityNumber())){
            throw new IdentityNumberIsAlreadyExistException("Identity Number with " + customerDto.getIdentityNumber() + " is already exist.");
        }
        if(customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber())){
            throw new PhoneNumberIsAlreadyExistException("Phone Number with " + customerDto.getPhoneNumber() + " is already exist.");
        }
        Customer customer = customerMapper.mapFromCustomerDtoToCustomer(customerDto);
        Customer saveCustomer = customerRepository.save(customer);
        log.info("Service: Application has been created.");
        applicationService.makeApplication(customer);
        log.info("Service: Sms sending has been completed.");
        return customerMapper.mapFromCustomerToCustomerDto(saveCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer controlCustomer = customerRepository.findById(customerDto.getId()).get();
        if(!controlCustomer.getIdentityNumber().equals(customerDto.getIdentityNumber())){
            if(customerRepository.existsByIdentityNumber(customerDto.getIdentityNumber())){
                throw new IdentityNumberIsAlreadyExistException("Identity Number with " + customerDto.getIdentityNumber() + " is already exist.");
            }
        }
        if(!controlCustomer.getPhoneNumber().equals(customerDto.getPhoneNumber())) {
            if (customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber())) {
                throw new PhoneNumberIsAlreadyExistException("Phone Number with " + customerDto.getPhoneNumber() + " is already exist.");
            }
        }
        Customer customer = customerMapper.mapFromCustomerDtoToCustomer(customerDto);
        Customer updateCustomer = customerRepository.save(customer);
        applicationService.update(customer, customerRepository.findByCustomerApplicationId(customer.getId()));
        log.info("Service: Application update handle has been completed");
        return customerMapper.mapFromCustomerToCustomerDto(updateCustomer);
    }

    @Override
    public void delete(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> customers = customerRepository.findAll();
        return customerMapper.mapFromCustomersToCustomerDto(customers);
    }

    @Override
    public CustomerDto get(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with id: %d not found.", id)));
        return customerMapper.mapFromCustomerToCustomerDto(customer);
    }
}
