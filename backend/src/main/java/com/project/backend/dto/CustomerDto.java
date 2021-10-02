package com.project.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private long id;

    @NotBlank(message = "Identity Number is mandatory")
    @Pattern(regexp="(^[0-9]{11}$)", message = "Identity Number must be 11 characters")
    @Pattern(regexp="(^\\d*[02468]$)", message = "Identity Number must be of digits and the last character must be an even number")
    private String identityNumber;

    @Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
    @Pattern(regexp="(^[a-zA-Z]{3,50}$)", message = "First Name must be of characters")
    private String firstName;

    @Size(min = 3, max = 50, message = "Last Name must be between 3 and 50 characters")
    @Pattern(regexp="(^[a-zA-Z]{3,50}$)", message = "Last Name must be of characters")
    private String lastName;

    @DecimalMax(value = "999999", message = "salary must be 999999 at most")
    @DecimalMin(value = "3000", message = "salary must be at least 3000")
    @Digits(integer=6, fraction=0)
    private BigDecimal salary;

    @NotBlank(message = "Phone Number is mandatory")
    @Pattern(regexp="(^[0-9]{10}$)", message = "Phone Number must be of digits and 10 characters")
    private String phoneNumber;
}