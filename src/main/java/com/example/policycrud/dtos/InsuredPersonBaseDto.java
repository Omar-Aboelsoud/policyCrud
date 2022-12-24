package com.example.policycrud.dtos;

import com.example.policycrud.dtos.validators.BigDecimalGreatZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public  class InsuredPersonBaseDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @BigDecimalGreatZero
    private BigDecimal premium;
}
