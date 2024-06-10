package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ApiIntegrationService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiIntegrationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ApiIntegrationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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

    private final ApiIntegrationMapper apiIntegrationMapper;

    public ApiIntegrationServiceImpl(ApiIntegrationRepository apiIntegrationRepository, ApiIntegrationMapper apiIntegrationMapper) {
        this.apiIntegrationRepository = apiIntegrationRepository;
        this.apiIntegrationMapper = apiIntegrationMapper;
    }

    @Override
    public ApiIntegrationDTO save(ApiIntegrationDTO apiIntegrationDTO) {
        log.debug("Request to save ApiIntegration : {}", apiIntegrationDTO);
        ApiIntegration apiIntegration = apiIntegrationMapper.toEntity(apiIntegrationDTO);
        apiIntegration = apiIntegrationRepository.save(apiIntegration);
        return apiIntegrationMapper.toDto(apiIntegration);
    }

    @Override
    public ApiIntegrationDTO update(ApiIntegrationDTO apiIntegrationDTO) {
        log.debug("Request to update ApiIntegration : {}", apiIntegrationDTO);
        ApiIntegration apiIntegration = apiIntegrationMapper.toEntity(apiIntegrationDTO);
        apiIntegration = apiIntegrationRepository.save(apiIntegration);
        return apiIntegrationMapper.toDto(apiIntegration);
    }

    @Override
    public Optional<ApiIntegrationDTO> partialUpdate(ApiIntegrationDTO apiIntegrationDTO) {
        log.debug("Request to partially update ApiIntegration : {}", apiIntegrationDTO);

        return apiIntegrationRepository
            .findById(apiIntegrationDTO.getId())
            .map(existingApiIntegration -> {
                apiIntegrationMapper.partialUpdate(existingApiIntegration, apiIntegrationDTO);

                return existingApiIntegration;
            })
            .map(apiIntegrationRepository::save)
            .map(apiIntegrationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiIntegrationDTO> findAll() {
        log.debug("Request to get all ApiIntegrations");
        return apiIntegrationRepository
            .findAll()
            .stream()
            .map(apiIntegrationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiIntegrationDTO> findOne(UUID id) {
        log.debug("Request to get ApiIntegration : {}", id);
        return apiIntegrationRepository.findById(id).map(apiIntegrationMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ApiIntegration : {}", id);
        apiIntegrationRepository.deleteById(id);
    }
}
