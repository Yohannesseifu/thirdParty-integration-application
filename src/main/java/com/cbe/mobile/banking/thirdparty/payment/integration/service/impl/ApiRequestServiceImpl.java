package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiRequestRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ApiRequestService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.ApiRequestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest}.
 */
@Service
@Transactional
public class ApiRequestServiceImpl implements ApiRequestService {

    private final Logger log = LoggerFactory.getLogger(ApiRequestServiceImpl.class);

    private final ApiRequestRepository apiRequestRepository;

    private final ApiRequestMapper apiRequestMapper;

    public ApiRequestServiceImpl(ApiRequestRepository apiRequestRepository, ApiRequestMapper apiRequestMapper) {
        this.apiRequestRepository = apiRequestRepository;
        this.apiRequestMapper = apiRequestMapper;
    }

    @Override
    public ApiRequestDTO save(ApiRequestDTO apiRequestDTO) {
        log.debug("Request to save ApiRequest : {}", apiRequestDTO);
        ApiRequest apiRequest = apiRequestMapper.toEntity(apiRequestDTO);
        apiRequest = apiRequestRepository.save(apiRequest);
        return apiRequestMapper.toDto(apiRequest);
    }

    @Override
    public ApiRequestDTO update(ApiRequestDTO apiRequestDTO) {
        log.debug("Request to update ApiRequest : {}", apiRequestDTO);
        ApiRequest apiRequest = apiRequestMapper.toEntity(apiRequestDTO);
        apiRequest = apiRequestRepository.save(apiRequest);
        return apiRequestMapper.toDto(apiRequest);
    }

    @Override
    public Optional<ApiRequestDTO> partialUpdate(ApiRequestDTO apiRequestDTO) {
        log.debug("Request to partially update ApiRequest : {}", apiRequestDTO);

        return apiRequestRepository
            .findById(apiRequestDTO.getId())
            .map(existingApiRequest -> {
                apiRequestMapper.partialUpdate(existingApiRequest, apiRequestDTO);

                return existingApiRequest;
            })
            .map(apiRequestRepository::save)
            .map(apiRequestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiRequestDTO> findAll() {
        log.debug("Request to get all ApiRequests");
        return apiRequestRepository.findAll().stream().map(apiRequestMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the apiRequests where PaymentDetail is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApiRequestDTO> findAllWherePaymentDetailIsNull() {
        log.debug("Request to get all apiRequests where PaymentDetail is null");
        return StreamSupport.stream(apiRequestRepository.findAll().spliterator(), false)
            .filter(apiRequest -> apiRequest.getPaymentDetail() == null)
            .map(apiRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiRequestDTO> findOne(UUID id) {
        log.debug("Request to get ApiRequest : {}", id);
        return apiRequestRepository.findById(id).map(apiRequestMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ApiRequest : {}", id);
        apiRequestRepository.deleteById(id);
    }
}
