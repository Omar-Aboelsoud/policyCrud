package com.example.policycrud.services;

import com.example.policycrud.dtos.InsuredPersonBaseDto;
import com.example.policycrud.dtos.InsuredPersonDto;
import com.example.policycrud.dtos.mappers.InsuredPersonMapper;
import com.example.policycrud.exception.LogicalException;
import com.example.policycrud.exception.ServerError;
import com.example.policycrud.models.entites.InsuredPerson;
import com.example.policycrud.models.entites.Policy;
import com.example.policycrud.models.repositories.InsuredPersonRepo;
import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuredPersonServiceImpl implements InsuredPersonService {

    Logger logger = LoggerFactory.getLogger(InsuredPersonServiceImpl.class);

    private final InsuredPersonMapper insuredPersonMapper;
    private final InsuredPersonRepo insuredPersonRepo;

    public InsuredPersonServiceImpl(InsuredPersonMapper insuredPersonMapper, InsuredPersonRepo insuredPersonRepo) {
        this.insuredPersonMapper = insuredPersonMapper;
        this.insuredPersonRepo = insuredPersonRepo;
    }

    @Override
    public List<InsuredPersonDto> createInsuredPersons(Policy policy, List<InsuredPersonBaseDto> insuredPersonBaseDtoList) {
        try {
            logger.info().message("create Insured Persons").field("insuredPersonBaseDtoList", insuredPersonBaseDtoList).log();
            List<InsuredPerson> savedInsuredPersonList = new ArrayList<>();
            insuredPersonBaseDtoList.stream()
                    .forEach(insuredPerson -> savedInsuredPersonList.add(createInsuredPerson(policy, insuredPerson)));
            logger.info().message("insured persons created and saved successfully").field("insuredPersonBaseDtoList", insuredPersonBaseDtoList).log();
            return insuredPersonMapper.map(savedInsuredPersonList);
        } catch (LogicalException e) {
            logger.error().exception("Logical Exception happened while creating insured persons List", e).field("insuredPersonBaseDtoList", insuredPersonBaseDtoList).log();
            throw new LogicalException(ServerError.FAILED_TO_CREATE_LIST_OF_INSURED_PERSONS);

        }
    }

    @Override
    public List<InsuredPersonDto> getInsuredPersonsByPolicyId(Long policyId) {
        try {
            logger.info().message("Getting insured persons by policy Id").field("policyId", policyId).log();
            List<InsuredPerson> insuredPersonList = this.insuredPersonRepo.findByPolicyId(policyId);
            return insuredPersonMapper.map(insuredPersonList);
        } catch (LogicalException e) {
            logger.error().exception("Logical exception happened while getting insured person by policy id", e).field("policyId", policyId).log();
            throw new LogicalException(ServerError.FAILED_TO_GET_INSURED_PERSONS_BY_POLICY_ID);
        }
    }


    private InsuredPerson createInsuredPerson(Policy policy, InsuredPersonBaseDto insuredPersonBaseDto) {
        try {
            InsuredPerson insuredPerson = insuredPersonMapper.map(insuredPersonBaseDto);
            insuredPerson.setPolicy(policy);
            return insuredPersonRepo.save(insuredPerson);

        } catch (LogicalException e) {
            logger.error().exception("Logical Exception happened while creating single insured person", e).field("insuredPersonBaseDto", insuredPersonBaseDto).log();
            throw new LogicalException(ServerError.FAILED_TO_CREATE_INSURED_PERSON);
        }

    }
}
