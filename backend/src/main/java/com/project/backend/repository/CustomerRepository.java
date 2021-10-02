package com.project.backend.repository;

import com.project.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT a.id FROM Application a WHERE a.customer.id = :id")
    long findByCustomerApplicationId(long id);

    boolean existsByIdentityNumber(String identityNumber);

    boolean existsByPhoneNumber(String identityNumber);
}
