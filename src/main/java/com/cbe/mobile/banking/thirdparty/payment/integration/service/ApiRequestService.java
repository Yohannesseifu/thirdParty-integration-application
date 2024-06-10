package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest}.
 */
public interface ApiRequestService {
    /**
     * Save a apiRequest.
     *
     * @param apiRequestDTO the entity to save.
     * @return the persisted entity.
     */
    ApiRequestDTO save(ApiRequestDTO apiRequestDTO);

    /**
     * Updates a apiRequest.
     *
     * @param apiRequestDTO the entity to update.
     * @return the persisted entity.
     */
    ApiRequestDTO update(ApiRequestDTO apiRequestDTO);

    /**
     * Partially updates a apiRequest.
     *
     * @param apiRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApiRequestDTO> partialUpdate(ApiRequestDTO apiRequestDTO);

    /**
     * Get all the apiRequests.
     *
     * @return the list of entities.
     */
    List<ApiRequestDTO> findAll();

    /**
     * Get all the ApiRequestDTO where PaymentDetail is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ApiRequestDTO> findAllWherePaymentDetailIsNull();

    /**
     * Get the "id" apiRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiRequestDTO> findOne(UUID id);

    /**
     * Delete the "id" apiRequest.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
