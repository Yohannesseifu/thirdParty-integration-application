package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ApiIntegrationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration}.
 */
@Service
@Transactional
public class ApiIntegrationServiceImpl implements ApiIntegrationService {

    private final Logger log = LoggerFactory.getLogger(ApiIntegrationServiceImpl.class);

    private final ApiIntegrationRepository apiIntegrationRepository;

    public ApiIntegrationServiceImpl(ApiIntegrationRepository apiIntegrationRepository) {
        this.apiIntegrationRepository = apiIntegrationRepository;
    }

    @Override
    public ApiIntegration save(ApiIntegration apiIntegration) {
        log.debug("Request to save ApiIntegration : {}", apiIntegration);
        return apiIntegrationRepository.save(apiIntegration);
    }

    @Override
    public ApiIntegration update(ApiIntegration apiIntegration) {
        log.debug("Request to update ApiIntegration : {}", apiIntegration);
        return apiIntegrationRepository.save(apiIntegration);
    }

    @Override
    public Optional<ApiIntegration> partialUpdate(ApiIntegration apiIntegration) {
        log.debug("Request to partially update ApiIntegration : {}", apiIntegration);

        return apiIntegrationRepository
            .findById(apiIntegration.getId())
            .map(existingApiIntegration -> {
                if (apiIntegration.getName() != null) {
                    existingApiIntegration.setName(apiIntegration.getName());
                }
                if (apiIntegration.getUrl() != null) {
                    existingApiIntegration.setUrl(apiIntegration.getUrl());
                }
                if (apiIntegration.getType() != null) {
                    existingApiIntegration.setType(apiIntegration.getType());
                }
                if (apiIntegration.getAuth() != null) {
                    existingApiIntegration.setAuth(apiIntegration.getAuth());
                }
                if (apiIntegration.getDescription() != null) {
                    existingApiIntegration.setDescription(apiIntegration.getDescription());
                }
                if (apiIntegration.getVersion() != null) {
                    existingApiIntegration.setVersion(apiIntegration.getVersion());
                }
                if (apiIntegration.getTimeout() != null) {
                    existingApiIntegration.setTimeout(apiIntegration.getTimeout());
                }
                if (apiIntegration.getRetryRetries() != null) {
                    existingApiIntegration.setRetryRetries(apiIntegration.getRetryRetries());
                }
                if (apiIntegration.getRetryDelay() != null) {
                    existingApiIntegration.setRetryDelay(apiIntegration.getRetryDelay());
                }

                return existingApiIntegration;
            })
            .map(apiIntegrationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiIntegration> findAll() {
        log.debug("Request to get all ApiIntegrations");
        return apiIntegrationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiIntegration> findOne(UUID id) {
        log.debug("Request to get ApiIntegration : {}", id);
        return apiIntegrationRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ApiIntegration : {}", id);
        apiIntegrationRepository.deleteById(id);
    }
}
