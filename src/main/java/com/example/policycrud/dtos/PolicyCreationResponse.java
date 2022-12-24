package com.example.policycrud.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PolicyCreationResponse {

    private String policyId;

    private List<InsuredPersonDto> insuredPersons;

    private BigDecimal totalPremium;
}
