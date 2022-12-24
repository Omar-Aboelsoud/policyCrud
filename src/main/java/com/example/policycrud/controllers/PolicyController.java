package com.example.policycrud.controllers;


import com.example.policycrud.dtos.*;
import com.example.policycrud.services.PolicyService;
import com.example.policycrud.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;

import static com.example.policycrud.controllers.PolicyController.POLICY_API_BASE_URL;

@RestController
@RequestMapping(POLICY_API_BASE_URL)
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    static final String POLICY_API_BASE_URL = Constants.API_BASE_URL + "/policies";


    @PostMapping
    public ResponseEntity<PolicyCreationResponse> createPolicy(@RequestBody @Valid PolicyCreationRequest policyCreationRequest){
        return new ResponseEntity<>(this.policyService.createNewPolicy(policyCreationRequest), HttpStatus.CREATED);
    }


    //I have added id as request parameter because it would be readable on api level that which api you resource requested to be updated
    // at the same time it is in the request body which nice to have [ could be removed] because it gives requested body the coherence.
    @PutMapping("/{policyId}")
    public ResponseEntity<PolicyModificationResponse> updatePolicyById(@PathVariable("policyId") String policyId,  @RequestBody @Valid PolicyModificationRequest policyModificationRequest){
        return new ResponseEntity<>(this.policyService.updatePolicyById(policyId,  policyModificationRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GetPolicyInformationResponse> getPoliciesInformation(@RequestParam(required = false) String policyId ,
                                                                               @RequestParam(required = false) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE)
    LocalDate requestedDate){
        return new ResponseEntity<>(this.policyService.getPolicyInformation(policyId,requestedDate),HttpStatus.OK);
    }

}
