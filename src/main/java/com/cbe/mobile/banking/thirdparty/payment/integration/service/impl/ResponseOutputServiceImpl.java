package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ResponseOutputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ResponseOutputService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ResponseOutputDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ResponseOutputMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput}.
 */
@Service
@Transactional
public class ResponseOutputServiceImpl implements ResponseOutputService {

    private final Logger log = LoggerFactory.getLogger(ResponseOutputServiceImpl.class);

    private final ResponseOutputRepository responseOutputRepository;

    private final ResponseOutputMapper responseOutputMapper;

    public ResponseOutputServiceImpl(ResponseOutputRepository responseOutputRepository, ResponseOutputMapper responseOutputMapper) {
        this.responseOutputRepository = responseOutputRepository;
        this.responseOutputMapper = responseOutputMapper;
    }

    @Override
    public ResponseOutputDTO save(ResponseOutputDTO responseOutputDTO) {
        log.debug("Request to save ResponseOutput : {}", responseOutputDTO);
        ResponseOutput responseOutput = responseOutputMapper.toEntity(responseOutputDTO);
        responseOutput = responseOutputRepository.save(responseOutput);
        return responseOutputMapper.toDto(responseOutput);
    }

    @Override
    public ResponseOutputDTO update(ResponseOutputDTO responseOutputDTO) {
        log.debug("Request to update ResponseOutput : {}", responseOutputDTO);
        ResponseOutput responseOutput = responseOutputMapper.toEntity(responseOutputDTO);
        responseOutput = responseOutputRepository.save(responseOutput);
        return responseOutputMapper.toDto(responseOutput);
    }

    @Override
    public Optional<ResponseOutputDTO> partialUpdate(ResponseOutputDTO responseOutputDTO) {
        log.debug("Request to partially update ResponseOutput : {}", responseOutputDTO);

        return responseOutputRepository
            .findById(responseOutputDTO.getId())
            .map(existingResponseOutput -> {
                responseOutputMapper.partialUpdate(existingResponseOutput, responseOutputDTO);

                return existingResponseOutput;
            })
            .map(responseOutputRepository::save)
            .map(responseOutputMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseOutputDTO> findAll() {
        log.debug("Request to get all ResponseOutputs");
        return responseOutputRepository
            .findAll()
            .stream()
            .map(responseOutputMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResponseOutputDTO> findOne(UUID id) {
        log.debug("Request to get ResponseOutput : {}", id);
        return responseOutputRepository.findById(id).map(responseOutputMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ResponseOutput : {}", id);
        responseOutputRepository.deleteById(id);
    }
}
