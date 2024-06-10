package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.RequestInputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.RequestInputService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.RequestInputDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.RequestInputMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput}.
 */
@Service
@Transactional
public class RequestInputServiceImpl implements RequestInputService {

    private final Logger log = LoggerFactory.getLogger(RequestInputServiceImpl.class);

    private final RequestInputRepository requestInputRepository;

    private final RequestInputMapper requestInputMapper;

    public RequestInputServiceImpl(RequestInputRepository requestInputRepository, RequestInputMapper requestInputMapper) {
        this.requestInputRepository = requestInputRepository;
        this.requestInputMapper = requestInputMapper;
    }

    @Override
    public RequestInputDTO save(RequestInputDTO requestInputDTO) {
        log.debug("Request to save RequestInput : {}", requestInputDTO);
        RequestInput requestInput = requestInputMapper.toEntity(requestInputDTO);
        requestInput = requestInputRepository.save(requestInput);
        return requestInputMapper.toDto(requestInput);
    }

    @Override
    public RequestInputDTO update(RequestInputDTO requestInputDTO) {
        log.debug("Request to update RequestInput : {}", requestInputDTO);
        RequestInput requestInput = requestInputMapper.toEntity(requestInputDTO);
        requestInput = requestInputRepository.save(requestInput);
        return requestInputMapper.toDto(requestInput);
    }

    @Override
    public Optional<RequestInputDTO> partialUpdate(RequestInputDTO requestInputDTO) {
        log.debug("Request to partially update RequestInput : {}", requestInputDTO);

        return requestInputRepository
            .findById(requestInputDTO.getId())
            .map(existingRequestInput -> {
                requestInputMapper.partialUpdate(existingRequestInput, requestInputDTO);

                return existingRequestInput;
            })
            .map(requestInputRepository::save)
            .map(requestInputMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestInputDTO> findAll() {
        log.debug("Request to get all RequestInputs");
        return requestInputRepository.findAll().stream().map(requestInputMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestInputDTO> findOne(UUID id) {
        log.debug("Request to get RequestInput : {}", id);
        return requestInputRepository.findById(id).map(requestInputMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete RequestInput : {}", id);
        requestInputRepository.deleteById(id);
    }
}
