package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationFieldMappingRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.IntegrationFieldMappingService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationFieldMappingDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.IntegrationFieldMappingMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping}.
 */
@Service
@Transactional
public class IntegrationFieldMappingServiceImpl implements IntegrationFieldMappingService {

    private final Logger log = LoggerFactory.getLogger(IntegrationFieldMappingServiceImpl.class);

    private final IntegrationFieldMappingRepository integrationFieldMappingRepository;

    private final IntegrationFieldMappingMapper integrationFieldMappingMapper;

    public IntegrationFieldMappingServiceImpl(
        IntegrationFieldMappingRepository integrationFieldMappingRepository,
        IntegrationFieldMappingMapper integrationFieldMappingMapper
    ) {
        this.integrationFieldMappingRepository = integrationFieldMappingRepository;
        this.integrationFieldMappingMapper = integrationFieldMappingMapper;
    }

    @Override
    public IntegrationFieldMappingDTO save(IntegrationFieldMappingDTO integrationFieldMappingDTO) {
        log.debug("Request to save IntegrationFieldMapping : {}", integrationFieldMappingDTO);
        IntegrationFieldMapping integrationFieldMapping = integrationFieldMappingMapper.toEntity(integrationFieldMappingDTO);
        integrationFieldMapping = integrationFieldMappingRepository.save(integrationFieldMapping);
        return integrationFieldMappingMapper.toDto(integrationFieldMapping);
    }

    @Override
    public IntegrationFieldMappingDTO update(IntegrationFieldMappingDTO integrationFieldMappingDTO) {
        log.debug("Request to update IntegrationFieldMapping : {}", integrationFieldMappingDTO);
        IntegrationFieldMapping integrationFieldMapping = integrationFieldMappingMapper.toEntity(integrationFieldMappingDTO);
        integrationFieldMapping = integrationFieldMappingRepository.save(integrationFieldMapping);
        return integrationFieldMappingMapper.toDto(integrationFieldMapping);
    }

    @Override
    public Optional<IntegrationFieldMappingDTO> partialUpdate(IntegrationFieldMappingDTO integrationFieldMappingDTO) {
        log.debug("Request to partially update IntegrationFieldMapping : {}", integrationFieldMappingDTO);

        return integrationFieldMappingRepository
            .findById(integrationFieldMappingDTO.getId())
            .map(existingIntegrationFieldMapping -> {
                integrationFieldMappingMapper.partialUpdate(existingIntegrationFieldMapping, integrationFieldMappingDTO);

                return existingIntegrationFieldMapping;
            })
            .map(integrationFieldMappingRepository::save)
            .map(integrationFieldMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationFieldMappingDTO> findAll() {
        log.debug("Request to get all IntegrationFieldMappings");
        return integrationFieldMappingRepository
            .findAll()
            .stream()
            .map(integrationFieldMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrationFieldMappingDTO> findOne(UUID id) {
        log.debug("Request to get IntegrationFieldMapping : {}", id);
        return integrationFieldMappingRepository.findById(id).map(integrationFieldMappingMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete IntegrationFieldMapping : {}", id);
        integrationFieldMappingRepository.deleteById(id);
    }
}
