package com.example.policycrud.dtos;


import com.example.policycrud.dtos.validators.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PolicyCreationRequest {

    @Future(message = "startDate should be in future")
    private LocalDate startDate;

    @Valid
    private List<InsuredPersonBaseDto> insuredPersons;



}
