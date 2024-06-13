package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ResponseOutputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ResponseOutputService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput}.
 */
@Service
@Transactional
public class ResponseOutputServiceImpl implements ResponseOutputService {

    private final Logger log = LoggerFactory.getLogger(ResponseOutputServiceImpl.class);

    private final ResponseOutputRepository responseOutputRepository;

    public ResponseOutputServiceImpl(ResponseOutputRepository responseOutputRepository) {
        this.responseOutputRepository = responseOutputRepository;
    }

    @Override
    public ResponseOutput save(ResponseOutput responseOutput) {
        log.debug("Request to save ResponseOutput : {}", responseOutput);
        return responseOutputRepository.save(responseOutput);
    }

    @Override
    public ResponseOutput update(ResponseOutput responseOutput) {
        log.debug("Request to update ResponseOutput : {}", responseOutput);
        return responseOutputRepository.save(responseOutput);
    }

    @Override
    public Optional<ResponseOutput> partialUpdate(ResponseOutput responseOutput) {
        log.debug("Request to partially update ResponseOutput : {}", responseOutput);

        return responseOutputRepository
            .findById(responseOutput.getId())
            .map(existingResponseOutput -> {
                if (responseOutput.getOutputName() != null) {
                    existingResponseOutput.setOutputName(responseOutput.getOutputName());
                }
                if (responseOutput.getDataType() != null) {
                    existingResponseOutput.setDataType(responseOutput.getDataType());
                }
                if (responseOutput.getResponseValuePath() != null) {
                    existingResponseOutput.setResponseValuePath(responseOutput.getResponseValuePath());
                }
                if (responseOutput.getResponseScope() != null) {
                    existingResponseOutput.setResponseScope(responseOutput.getResponseScope());
                }
                if (responseOutput.getTransferCoreMapping() != null) {
                    existingResponseOutput.setTransferCoreMapping(responseOutput.getTransferCoreMapping());
                }
                if (responseOutput.getIsLogicField() != null) {
                    existingResponseOutput.setIsLogicField(responseOutput.getIsLogicField());
                }
                if (responseOutput.getConstantValueToCompare() != null) {
                    existingResponseOutput.setConstantValueToCompare(responseOutput.getConstantValueToCompare());
                }
                if (responseOutput.getOperatorToCompareValue() != null) {
                    existingResponseOutput.setOperatorToCompareValue(responseOutput.getOperatorToCompareValue());
                }
                if (responseOutput.getIsRequired() != null) {
                    existingResponseOutput.setIsRequired(responseOutput.getIsRequired());
                }

                return existingResponseOutput;
            })
            .map(responseOutputRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseOutput> findAll() {
        log.debug("Request to get all ResponseOutputs");
        return responseOutputRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponseOutput> findOne(UUID id) {
        log.debug("Request to get ResponseOutput : {}", id);
        return responseOutputRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ResponseOutput : {}", id);
        responseOutputRepository.deleteById(id);
    }
}
