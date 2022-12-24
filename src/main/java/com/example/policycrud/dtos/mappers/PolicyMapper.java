package com.example.policycrud.dtos.mappers;

import com.example.policycrud.config.MapStructConfig;
import com.example.policycrud.dtos.GetPolicyInformationResponse;
import com.example.policycrud.dtos.PolicyCreationResponse;
import com.example.policycrud.dtos.PolicyModificationRequest;
import com.example.policycrud.dtos.PolicyModificationResponse;
import com.example.policycrud.models.entites.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(config = MapStructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {BigDecimal.class})
public abstract class PolicyMapper {

    public abstract PolicyCreationResponse map(Policy policy);

    @Mapping(target = "startDate",source = "effectiveDate")
    public abstract Policy map(PolicyModificationRequest policyModificationRequest);

    public abstract PolicyModificationResponse mapToModificationResponse(Policy policy);

    public abstract GetPolicyInformationResponse mapToGetPolicyInformationResponse(Policy policy);


}
