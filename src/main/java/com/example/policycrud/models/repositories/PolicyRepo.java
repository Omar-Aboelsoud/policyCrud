package com.example.policycrud.models.repositories;

import com.example.policycrud.models.entites.Policy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PolicyRepo extends PagingAndSortingRepository<Policy, Long> {

    Optional<Policy> findByPolicyId(String policyId);

    @Query("select p from Policy  as p where ((:policyId) is  null or p.policyId = :policyId) " +
            " and (p.startDate=:requestDate) ")
    Optional<Policy> getPolicyByIdAndStartDate(String policyId, LocalDate requestDate);
}
