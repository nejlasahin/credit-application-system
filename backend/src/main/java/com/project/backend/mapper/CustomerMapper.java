package com.project.backend.mapper;

import com.project.backend.dto.CustomerDto;
import com.project.backend.model.Customer;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Named("mapFromCustomerDtoToCustomer")
    Customer mapFromCustomerDtoToCustomer(CustomerDto customerDto);

    @Named("mapFromCustomerToCustomerDto")
    CustomerDto mapFromCustomerToCustomerDto(Customer customer);

    @IterableMapping(qualifiedByName = "mapFromCustomerToCustomerDto")
    List<CustomerDto> mapFromCustomersToCustomerDto(List<Customer> customers);

}
