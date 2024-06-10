package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ThirdPartyIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ThirdPartyIntegrationService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ThirdPartyIntegrationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ThirdPartyIntegrationMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration}.
 */
@Service
@Transactional
public class ThirdPartyIntegrationServiceImpl implements ThirdPartyIntegrationService {

    private final Logger log = LoggerFactory.getLogger(ThirdPartyIntegrationServiceImpl.class);

    private final ThirdPartyIntegrationRepository thirdPartyIntegrationRepository;

    private final ThirdPartyIntegrationMapper thirdPartyIntegrationMapper;

    public ThirdPartyIntegrationServiceImpl(
        ThirdPartyIntegrationRepository thirdPartyIntegrationRepository,
        ThirdPartyIntegrationMapper thirdPartyIntegrationMapper
    ) {
        this.thirdPartyIntegrationRepository = thirdPartyIntegrationRepository;
        this.thirdPartyIntegrationMapper = thirdPartyIntegrationMapper;
    }

    @Override
    public ThirdPartyIntegrationDTO save(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO) {
        log.debug("Request to save ThirdPartyIntegration : {}", thirdPartyIntegrationDTO);
        ThirdPartyIntegration thirdPartyIntegration = thirdPartyIntegrationMapper.toEntity(thirdPartyIntegrationDTO);
        thirdPartyIntegration = thirdPartyIntegrationRepository.save(thirdPartyIntegration);
        return thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);
    }

    @Override
    public ThirdPartyIntegrationDTO update(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO) {
        log.debug("Request to update ThirdPartyIntegration : {}", thirdPartyIntegrationDTO);
        ThirdPartyIntegration thirdPartyIntegration = thirdPartyIntegrationMapper.toEntity(thirdPartyIntegrationDTO);
        thirdPartyIntegration = thirdPartyIntegrationRepository.save(thirdPartyIntegration);
        return thirdPartyIntegrationMapper.toDto(thirdPartyIntegration);
    }

    @Override
    public Optional<ThirdPartyIntegrationDTO> partialUpdate(ThirdPartyIntegrationDTO thirdPartyIntegrationDTO) {
        log.debug("Request to partially update ThirdPartyIntegration : {}", thirdPartyIntegrationDTO);

        return thirdPartyIntegrationRepository
            .findById(thirdPartyIntegrationDTO.getId())
            .map(existingThirdPartyIntegration -> {
                thirdPartyIntegrationMapper.partialUpdate(existingThirdPartyIntegration, thirdPartyIntegrationDTO);

                return existingThirdPartyIntegration;
            })
            .map(thirdPartyIntegrationRepository::save)
            .map(thirdPartyIntegrationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThirdPartyIntegrationDTO> findAll() {
        log.debug("Request to get all ThirdPartyIntegrations");
        return thirdPartyIntegrationRepository
            .findAll()
            .stream()
            .map(thirdPartyIntegrationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThirdPartyIntegrationDTO> findOne(UUID id) {
        log.debug("Request to get ThirdPartyIntegration : {}", id);
        return thirdPartyIntegrationRepository.findById(id).map(thirdPartyIntegrationMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ThirdPartyIntegration : {}", id);
        thirdPartyIntegrationRepository.deleteById(id);
    }
}
