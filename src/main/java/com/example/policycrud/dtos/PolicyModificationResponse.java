package com.example.policycrud.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyModificationResponse {

    private String policyId;

    private LocalDate effectiveDate;

    private List<InsuredPersonDto> insuredPersons;

    private BigDecimal totalPremium;
}
