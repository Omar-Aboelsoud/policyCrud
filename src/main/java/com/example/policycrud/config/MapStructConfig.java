package com.example.policycrud.config;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        builder = @Builder(disableBuilder = true)
)
public class MapStructConfig {
}