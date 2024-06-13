package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationFieldMappingRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.IntegrationFieldMappingService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping}.
 */
@Service
@Transactional
public class IntegrationFieldMappingServiceImpl implements IntegrationFieldMappingService {

    private final Logger log = LoggerFactory.getLogger(IntegrationFieldMappingServiceImpl.class);

    private final IntegrationFieldMappingRepository integrationFieldMappingRepository;

    public IntegrationFieldMappingServiceImpl(IntegrationFieldMappingRepository integrationFieldMappingRepository) {
        this.integrationFieldMappingRepository = integrationFieldMappingRepository;
    }

    @Override
    public IntegrationFieldMapping save(IntegrationFieldMapping integrationFieldMapping) {
        log.debug("Request to save IntegrationFieldMapping : {}", integrationFieldMapping);
        return integrationFieldMappingRepository.save(integrationFieldMapping);
    }

    @Override
    public IntegrationFieldMapping update(IntegrationFieldMapping integrationFieldMapping) {
        log.debug("Request to update IntegrationFieldMapping : {}", integrationFieldMapping);
        return integrationFieldMappingRepository.save(integrationFieldMapping);
    }

    @Override
    public Optional<IntegrationFieldMapping> partialUpdate(IntegrationFieldMapping integrationFieldMapping) {
        log.debug("Request to partially update IntegrationFieldMapping : {}", integrationFieldMapping);

        return integrationFieldMappingRepository.findById(integrationFieldMapping.getId()).map(integrationFieldMappingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationFieldMapping> findAll() {
        log.debug("Request to get all IntegrationFieldMappings");
        return integrationFieldMappingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrationFieldMapping> findOne(UUID id) {
        log.debug("Request to get IntegrationFieldMapping : {}", id);
        return integrationFieldMappingRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete IntegrationFieldMapping : {}", id);
        integrationFieldMappingRepository.deleteById(id);
    }
}
