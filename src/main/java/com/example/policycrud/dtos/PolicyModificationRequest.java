package com.example.policycrud.dtos;


import com.example.policycrud.dtos.validators.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyModificationRequest {


    @NotBlank
    private String policyId;

    @Future(message = "effectiveDate should be in future")
    private LocalDate effectiveDate;
    @Valid
    @NotEmpty
    private List<InsuredPersonDto> insuredPersons;

}
