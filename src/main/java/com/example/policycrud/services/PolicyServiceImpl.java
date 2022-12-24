package com.example.policycrud.services;

import com.example.policycrud.dtos.*;
import com.example.policycrud.dtos.mappers.PolicyMapper;
import com.example.policycrud.exception.LogicalException;
import com.example.policycrud.exception.ServerError;
import com.example.policycrud.models.entites.Policy;
import com.example.policycrud.models.repositories.PolicyRepo;
import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PolicyServiceImpl implements PolicyService {

    Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);

    private final PolicyRepo policyRepo;


    private final PolicyMapper policyMapper;

    private final InsuredPersonService insuredPersonService;


    public PolicyServiceImpl(PolicyRepo policyRepo, PolicyMapper policyMapper, InsuredPersonService insuredPersonService) {
        this.policyRepo = policyRepo;
        this.policyMapper = policyMapper;
        this.insuredPersonService = insuredPersonService;
    }

    @Override
    public PolicyCreationResponse createNewPolicy(PolicyCreationRequest policyCreationRequest) {
        try {
            logger.info().message("create new policy").field("policyCreationRequest", policyCreationRequest).log();
            Policy policy = new Policy();
            policy.setStartDate(policyCreationRequest.getStartDate());
            policy.setPolicyId(this.generatePolicyId());
            Policy savedPolicy = policyRepo.save(policy);
            List<InsuredPersonDto> insuredPersonDtoList = insuredPersonService.createInsuredPersons(savedPolicy, policyCreationRequest.getInsuredPersons());

            logger.info().message("new policy has been created and saved").field("policy", savedPolicy).log();
            BigDecimal totalPremium = insuredPersonDtoList.stream().map(InsuredPersonDto::getPremium).reduce(BigDecimal.ZERO, BigDecimal::add);
            List<InsuredPersonDto> savedInsuredPersonsDtoList = insuredPersonService.getInsuredPersonsByPolicyId(policy.getId());
            PolicyCreationResponse policyCreationResponse = policyMapper.map(savedPolicy);
            policyCreationResponse.setTotalPremium(totalPremium);
            policyCreationResponse.setInsuredPersons(savedInsuredPersonsDtoList);
            return policyCreationResponse;
        } catch (LogicalException e) {
            logger.error().exception("LogicalException happened while creating new policy", e).field("policyCreationRequest", policyCreationRequest).log();
            throw new LogicalException(ServerError.FAILED_TO_CREATE_NEW_POLICY);
        }
    }

    @Override
    public PolicyModificationResponse updatePolicyById(String policyId, PolicyModificationRequest policyModificationRequest) {
        try {

            logger.info().message("update policy by Id").field("policyId", policyId).field("policyModificationRequest", policyModificationRequest).log();
            Optional<Policy> policy = policyRepo.findByPolicyId(policyId);
            if (policy.isEmpty()) {
                logger.info().message("policy is not exist").field("policyId", policyId).log();
                throw new LogicalException(ServerError.POLICY_IS_NOT_EXIST).apply(policyId);
            }

            Policy updatedPolicy= policyMapper.map(policyModificationRequest);
            policy.get().setPolicyId(policyModificationRequest.getPolicyId());
            policy.get().setStartDate(policyModificationRequest.getEffectiveDate());
            policy.get().setInsuredPersons(updatedPolicy.getInsuredPersons());

            return policyMapper.mapToModificationResponse(policy.get());

        } catch (LogicalException e) {
            logger.error().exception("LogicalException happened while updating policy by id", e).field("policyId", policyId)
                    .field("policyService", policyModificationRequest)
                    .log();
            throw new LogicalException(ServerError.FAILED_TO_UPDATE_POLICY_BY_ID);
        }
    }

    @Override
    public GetPolicyInformationResponse getPolicyInformation(String policyId, LocalDate requestedDate) {
        try {
            if (Objects.isNull(requestedDate)) {
                requestedDate = LocalDate.now();
            }
            Optional<Policy> policy = policyRepo.getPolicyByIdAndStartDate(policyId, requestedDate);
            GetPolicyInformationResponse policyInformationResponse = policyMapper.mapToGetPolicyInformationResponse(policy.orElse(new Policy()));

            policyInformationResponse.setRequestDate(requestedDate);
            return policyInformationResponse;

        } catch (LogicalException e) {
            logger.error().exception("LogicalException happened while getting policy ", e)
                    .field("policyId", policyId)
                    .field("requestedDate", requestedDate)
                    .log();
            throw new LogicalException(ServerError.FAILED_TO_GET_POLICY_INFORMATION);
        }
    }

    // I am not considering the collison here for the simplicity of the project
    private String generatePolicyId() {
        return UUID.randomUUID().toString();
    }
}
