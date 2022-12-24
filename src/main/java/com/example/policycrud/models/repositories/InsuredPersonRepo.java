package com.example.policycrud.models.repositories;

import com.example.policycrud.models.entites.InsuredPerson;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuredPersonRepo extends PagingAndSortingRepository<InsuredPerson, Long> {

    List<InsuredPerson> findByPolicyId(Long policyId);
}
