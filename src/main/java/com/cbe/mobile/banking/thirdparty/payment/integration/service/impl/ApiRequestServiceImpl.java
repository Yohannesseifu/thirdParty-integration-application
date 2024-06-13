package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiRequestRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ApiRequestService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public ApiRequestServiceImpl(ApiRequestRepository apiRequestRepository) {
        this.apiRequestRepository = apiRequestRepository;
    }

    @Override
    public ApiRequest save(ApiRequest apiRequest) {
        log.debug("Request to save ApiRequest : {}", apiRequest);
        return apiRequestRepository.save(apiRequest);
    }

    @Override
    public ApiRequest update(ApiRequest apiRequest) {
        log.debug("Request to update ApiRequest : {}", apiRequest);
        return apiRequestRepository.save(apiRequest);
    }

    @Override
    public Optional<ApiRequest> partialUpdate(ApiRequest apiRequest) {
        log.debug("Request to partially update ApiRequest : {}", apiRequest);

        return apiRequestRepository
            .findById(apiRequest.getId())
            .map(existingApiRequest -> {
                if (apiRequest.getUri() != null) {
                    existingApiRequest.setUri(apiRequest.getUri());
                }
                if (apiRequest.getBody() != null) {
                    existingApiRequest.setBody(apiRequest.getBody());
                }
                if (apiRequest.getMethod() != null) {
                    existingApiRequest.setMethod(apiRequest.getMethod());
                }

                return existingApiRequest;
            })
            .map(apiRequestRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiRequest> findAll() {
        log.debug("Request to get all ApiRequests");
        return apiRequestRepository.findAll();
    }

    /**
     *  Get all the apiRequests where PaymentDetail is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApiRequest> findAllWherePaymentDetailIsNull() {
        log.debug("Request to get all apiRequests where PaymentDetail is null");
        return StreamSupport.stream(apiRequestRepository.findAll().spliterator(), false)
            .filter(apiRequest -> apiRequest.getPaymentDetail() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApiRequest> findOne(UUID id) {
        log.debug("Request to get ApiRequest : {}", id);
        return apiRequestRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ApiRequest : {}", id);
        apiRequestRepository.deleteById(id);
    }
}
