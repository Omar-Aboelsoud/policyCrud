package com.example.policycrud.dtos;


// I think we should use query parameters instead of creation request

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPolicyInformationRequest {
    private String policyId;
    private LocalDate requestDate;
}
