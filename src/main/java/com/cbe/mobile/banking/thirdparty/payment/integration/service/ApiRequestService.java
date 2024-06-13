package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
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
     * @param apiRequest the entity to save.
     * @return the persisted entity.
     */
    ApiRequest save(ApiRequest apiRequest);

    /**
     * Updates a apiRequest.
     *
     * @param apiRequest the entity to update.
     * @return the persisted entity.
     */
    ApiRequest update(ApiRequest apiRequest);

    /**
     * Partially updates a apiRequest.
     *
     * @param apiRequest the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApiRequest> partialUpdate(ApiRequest apiRequest);

    /**
     * Get all the apiRequests.
     *
     * @return the list of entities.
     */
    List<ApiRequest> findAll();

    /**
     * Get all the ApiRequest where PaymentDetail is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ApiRequest> findAllWherePaymentDetailIsNull();

    /**
     * Get the "id" apiRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiRequest> findOne(UUID id);

    /**
     * Delete the "id" apiRequest.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
