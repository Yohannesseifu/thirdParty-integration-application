package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentParamDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam}.
 */
public interface PaymentParamService {
    /**
     * Save a paymentParam.
     *
     * @param paymentParamDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentParamDTO save(PaymentParamDTO paymentParamDTO);

    /**
     * Updates a paymentParam.
     *
     * @param paymentParamDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentParamDTO update(PaymentParamDTO paymentParamDTO);

    /**
     * Partially updates a paymentParam.
     *
     * @param paymentParamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentParamDTO> partialUpdate(PaymentParamDTO paymentParamDTO);

    /**
     * Get all the paymentParams.
     *
     * @return the list of entities.
     */
    List<PaymentParamDTO> findAll();

    /**
     * Get the "id" paymentParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentParamDTO> findOne(UUID id);

    /**
     * Delete the "id" paymentParam.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
