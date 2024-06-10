package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.HeaderRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.HeaderService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.HeaderDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.HeaderMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header}.
 */
@Service
@Transactional
public class HeaderServiceImpl implements HeaderService {

    private final Logger log = LoggerFactory.getLogger(HeaderServiceImpl.class);

    private final HeaderRepository headerRepository;

    private final HeaderMapper headerMapper;

    public HeaderServiceImpl(HeaderRepository headerRepository, HeaderMapper headerMapper) {
        this.headerRepository = headerRepository;
        this.headerMapper = headerMapper;
    }

    @Override
    public HeaderDTO save(HeaderDTO headerDTO) {
        log.debug("Request to save Header : {}", headerDTO);
        Header header = headerMapper.toEntity(headerDTO);
        header = headerRepository.save(header);
        return headerMapper.toDto(header);
    }

    @Override
    public HeaderDTO update(HeaderDTO headerDTO) {
        log.debug("Request to update Header : {}", headerDTO);
        Header header = headerMapper.toEntity(headerDTO);
        header = headerRepository.save(header);
        return headerMapper.toDto(header);
    }

    @Override
    public Optional<HeaderDTO> partialUpdate(HeaderDTO headerDTO) {
        log.debug("Request to partially update Header : {}", headerDTO);

        return headerRepository
            .findById(headerDTO.getId())
            .map(existingHeader -> {
                headerMapper.partialUpdate(existingHeader, headerDTO);

                return existingHeader;
            })
            .map(headerRepository::save)
            .map(headerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HeaderDTO> findAll() {
        log.debug("Request to get all Headers");
        return headerRepository.findAll().stream().map(headerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HeaderDTO> findOne(UUID id) {
        log.debug("Request to get Header : {}", id);
        return headerRepository.findById(id).map(headerMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Header : {}", id);
        headerRepository.deleteById(id);
    }
}
