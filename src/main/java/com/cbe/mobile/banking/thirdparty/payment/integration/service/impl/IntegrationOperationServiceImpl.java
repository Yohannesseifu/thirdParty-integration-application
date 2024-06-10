package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationOperationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.IntegrationOperationService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationOperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.IntegrationOperationMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation}.
 */
@Service
@Transactional
public class IntegrationOperationServiceImpl implements IntegrationOperationService {

    private final Logger log = LoggerFactory.getLogger(IntegrationOperationServiceImpl.class);

    private final IntegrationOperationRepository integrationOperationRepository;

    private final IntegrationOperationMapper integrationOperationMapper;

    public IntegrationOperationServiceImpl(
        IntegrationOperationRepository integrationOperationRepository,
        IntegrationOperationMapper integrationOperationMapper
    ) {
        this.integrationOperationRepository = integrationOperationRepository;
        this.integrationOperationMapper = integrationOperationMapper;
    }

    @Override
    public IntegrationOperationDTO save(IntegrationOperationDTO integrationOperationDTO) {
        log.debug("Request to save IntegrationOperation : {}", integrationOperationDTO);
        IntegrationOperation integrationOperation = integrationOperationMapper.toEntity(integrationOperationDTO);
        integrationOperation = integrationOperationRepository.save(integrationOperation);
        return integrationOperationMapper.toDto(integrationOperation);
    }

    @Override
    public IntegrationOperationDTO update(IntegrationOperationDTO integrationOperationDTO) {
        log.debug("Request to update IntegrationOperation : {}", integrationOperationDTO);
        IntegrationOperation integrationOperation = integrationOperationMapper.toEntity(integrationOperationDTO);
        integrationOperation = integrationOperationRepository.save(integrationOperation);
        return integrationOperationMapper.toDto(integrationOperation);
    }

    @Override
    public Optional<IntegrationOperationDTO> partialUpdate(IntegrationOperationDTO integrationOperationDTO) {
        log.debug("Request to partially update IntegrationOperation : {}", integrationOperationDTO);

        return integrationOperationRepository
            .findById(integrationOperationDTO.getId())
            .map(existingIntegrationOperation -> {
                integrationOperationMapper.partialUpdate(existingIntegrationOperation, integrationOperationDTO);

                return existingIntegrationOperation;
            })
            .map(integrationOperationRepository::save)
            .map(integrationOperationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationOperationDTO> findAll() {
        log.debug("Request to get all IntegrationOperations");
        return integrationOperationRepository
            .findAll()
            .stream()
            .map(integrationOperationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrationOperationDTO> findOne(UUID id) {
        log.debug("Request to get IntegrationOperation : {}", id);
        return integrationOperationRepository.findById(id).map(integrationOperationMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete IntegrationOperation : {}", id);
        integrationOperationRepository.deleteById(id);
    }
}
