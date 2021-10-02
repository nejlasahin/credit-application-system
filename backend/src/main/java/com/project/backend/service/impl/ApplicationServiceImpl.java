package com.project.backend.service.impl;

import com.project.backend.dto.ApplicationDto;
import com.project.backend.enums.CreditResult;
import com.project.backend.exceptions.ResourceNotFoundException;
import com.project.backend.mapper.ApplicationMapper;
import com.project.backend.model.Application;
import com.project.backend.model.Customer;
import com.project.backend.repository.ApplicationRepository;
import com.project.backend.util.rules.ApplicationRule;
import com.project.backend.service.ApplicationService;
import com.project.backend.util.notificationService.NotificationService;
import com.project.backend.util.scoreService.ScoreService;

import lombok.extern.slf4j.Slf4j;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ScoreService scoreService;
    private final NotificationService notificationService;
   
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper,
			ScoreService scoreService, NotificationService notificationService) {
		this.applicationRepository = applicationRepository;
		this.applicationMapper = applicationMapper;
		this.scoreService = scoreService;
		this.notificationService = notificationService;
	}
    

    @Override
    public ApplicationDto makeApplication(Customer customer) {
    	Pair<BigDecimal, CreditResult> result = applicationResult(scoreService.getScore(customer.getIdentityNumber()), customer.getSalary());
        Application application = Application.builder().customer(customer).creditLimit(result.getValue0()).creditResult(result.getValue1()).build();
        Boolean status = (application.getCreditResult() == CreditResult.CONFIRMED);
        notificationService.sendSms(customer.getPhoneNumber(), status);
        log.info("Service: Sms sending has been completed.");
        return applicationMapper.mapFromApplicationToApplicationDto(applicationRepository.save(application));
    }

    @Override
    public ApplicationDto update(Customer customer, long applicationId) {
        Pair<BigDecimal, CreditResult> result = applicationResult(scoreService.getScore(customer.getIdentityNumber()), customer.getSalary());
        Application application = Application.builder().customer(customer).creditLimit(result.getValue0()).creditResult(result.getValue1()).build();
        Boolean status = (application.getCreditResult() == CreditResult.CONFIRMED);
        application.setId(applicationId);
        notificationService.sendSms(customer.getPhoneNumber(), status);
        log.info("Service: Sms sending has been completed.");
        return applicationMapper.mapFromApplicationToApplicationDto(applicationRepository.save(application));
    }

    @Override
    public List<ApplicationDto> getAll() {
        List<Application> applications = applicationRepository.findAll();
        return applicationMapper.mapFromApplicationsToApplicationDto(applications);
    }

    @Override
    public ApplicationDto getStatus(String identityNumber) {
        Application application = applicationRepository.findByCustomerIdentityNumber(identityNumber);
        if(Objects.isNull(application)){
            throw new ResourceNotFoundException("Application with identity number: " + identityNumber + " not found.");
        }
        return applicationMapper.mapFromApplicationToApplicationDto(application);
    }

    @Override
    public Pair<BigDecimal, CreditResult> applicationResult(int score, BigDecimal salary) {
        if(score < 500){
            return new Pair<>(BigDecimal.valueOf(0), CreditResult.UNCONFIRMED);
        }else if(score >= 500 && score < 1000 && salary.intValue() <= 5000){
            return new Pair<>(BigDecimal.valueOf(10000), CreditResult.CONFIRMED);
        }else if(score >= 500 && score < 1000 && salary.intValue() > 5000){
            return new Pair<>(BigDecimal.valueOf(20000), CreditResult.CONFIRMED);
        }else if(score >= 1000){
            return new Pair<>((salary.multiply(BigDecimal.valueOf(ApplicationRule.CREDIT_LIMIT_MULTIPLIER))), CreditResult.CONFIRMED);
        }
        return new Pair<>(BigDecimal.valueOf(0), CreditResult.UNCONFIRMED);
    }

}
