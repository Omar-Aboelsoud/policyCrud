package com.example.policycrud.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuredPersonDto extends InsuredPersonBaseDto{
    private Long id;
}
