package com.example.policycrud.dtos.mappers;


import com.example.policycrud.config.MapStructConfig;
import com.example.policycrud.dtos.InsuredPersonBaseDto;
import com.example.policycrud.dtos.InsuredPersonDto;
import com.example.policycrud.models.entites.InsuredPerson;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapStructConfig.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class InsuredPersonMapper {

    public abstract InsuredPerson map(InsuredPersonBaseDto insuredPersonBaseDto);

    public abstract InsuredPerson map(InsuredPerson insuredPerson);

    public abstract List<InsuredPersonDto> map (List<InsuredPerson> insuredPersonList);

}
