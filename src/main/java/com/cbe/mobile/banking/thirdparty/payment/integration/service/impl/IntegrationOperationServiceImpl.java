package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationOperationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.IntegrationOperationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation}.
 */
@Service
@Transactional
public class IntegrationOperationServiceImpl implements IntegrationOperationService {

    private final Logger log = LoggerFactory.getLogger(IntegrationOperationServiceImpl.class);

    private final IntegrationOperationRepository integrationOperationRepository;

    public IntegrationOperationServiceImpl(IntegrationOperationRepository integrationOperationRepository) {
        this.integrationOperationRepository = integrationOperationRepository;
    }

    @Override
    public IntegrationOperation save(IntegrationOperation integrationOperation) {
        log.debug("Request to save IntegrationOperation : {}", integrationOperation);
        return integrationOperationRepository.save(integrationOperation);
    }

    @Override
    public IntegrationOperation update(IntegrationOperation integrationOperation) {
        log.debug("Request to update IntegrationOperation : {}", integrationOperation);
        return integrationOperationRepository.save(integrationOperation);
    }

    @Override
    public Optional<IntegrationOperation> partialUpdate(IntegrationOperation integrationOperation) {
        log.debug("Request to partially update IntegrationOperation : {}", integrationOperation);

        return integrationOperationRepository
            .findById(integrationOperation.getId())
            .map(existingIntegrationOperation -> {
                if (integrationOperation.getOperationType() != null) {
                    existingIntegrationOperation.setOperationType(integrationOperation.getOperationType());
                }

                return existingIntegrationOperation;
            })
            .map(integrationOperationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationOperation> findAll() {
        log.debug("Request to get all IntegrationOperations");
        return integrationOperationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrationOperation> findOne(UUID id) {
        log.debug("Request to get IntegrationOperation : {}", id);
        return integrationOperationRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete IntegrationOperation : {}", id);
        integrationOperationRepository.deleteById(id);
    }
}
