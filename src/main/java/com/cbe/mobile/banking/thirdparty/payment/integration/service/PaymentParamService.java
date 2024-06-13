package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
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
     * @param paymentParam the entity to save.
     * @return the persisted entity.
     */
    PaymentParam save(PaymentParam paymentParam);

    /**
     * Updates a paymentParam.
     *
     * @param paymentParam the entity to update.
     * @return the persisted entity.
     */
    PaymentParam update(PaymentParam paymentParam);

    /**
     * Partially updates a paymentParam.
     *
     * @param paymentParam the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentParam> partialUpdate(PaymentParam paymentParam);

    /**
     * Get all the paymentParams.
     *
     * @return the list of entities.
     */
    List<PaymentParam> findAll();

    /**
     * Get the "id" paymentParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentParam> findOne(UUID id);

    /**
     * Delete the "id" paymentParam.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
