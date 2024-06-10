package com.cbe.mobile.banking.thirdparty.payment.integration.service;

import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentDetailDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail}.
 */
public interface PaymentDetailService {
    /**
     * Save a paymentDetail.
     *
     * @param paymentDetailDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentDetailDTO save(PaymentDetailDTO paymentDetailDTO);

    /**
     * Updates a paymentDetail.
     *
     * @param paymentDetailDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentDetailDTO update(PaymentDetailDTO paymentDetailDTO);

    /**
     * Partially updates a paymentDetail.
     *
     * @param paymentDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentDetailDTO> partialUpdate(PaymentDetailDTO paymentDetailDTO);

    /**
     * Get all the paymentDetails.
     *
     * @return the list of entities.
     */
    List<PaymentDetailDTO> findAll();

    /**
     * Get the "id" paymentDetail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentDetailDTO> findOne(UUID id);

    /**
     * Delete the "id" paymentDetail.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
