package com.example.policycrud.services;

import com.example.policycrud.dtos.InsuredPersonBaseDto;
import com.example.policycrud.dtos.InsuredPersonDto;
import com.example.policycrud.models.entites.InsuredPerson;
import com.example.policycrud.models.entites.Policy;

import java.util.List;

public interface InsuredPersonService {

     List<InsuredPersonDto> createInsuredPersons(Policy policy, List<InsuredPersonBaseDto> insuredPersonBaseDtoList);

     List<InsuredPersonDto> getInsuredPersonsByPolicyId(Long policyId);
}
