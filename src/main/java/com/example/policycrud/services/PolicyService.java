package com.example.policycrud.services;

import com.example.policycrud.dtos.*;

import java.time.LocalDate;

public interface PolicyService {
    PolicyCreationResponse createNewPolicy(PolicyCreationRequest policyCreationRequest);

    PolicyModificationResponse updatePolicyById(String policyId, PolicyModificationRequest policyService);

    GetPolicyInformationResponse getPolicyInformation(String policyId, LocalDate requestedDate);

}
