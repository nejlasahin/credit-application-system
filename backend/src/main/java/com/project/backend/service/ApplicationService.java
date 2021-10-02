package com.project.backend.service;

import com.project.backend.dto.ApplicationDto;
import com.project.backend.enums.CreditResult;
import com.project.backend.model.Customer;

import java.math.BigDecimal;
import java.util.List;

import org.javatuples.Pair;

public interface ApplicationService {
    ApplicationDto makeApplication(Customer customer);
    ApplicationDto update(Customer customer, long applicationId);
    List<ApplicationDto> getAll();
    ApplicationDto getStatus(String identityNumber);
    Pair<BigDecimal, CreditResult> applicationResult(int score, BigDecimal salary);

}

	