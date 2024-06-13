package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentDetailRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentDetailService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail}.
 */
@Service
@Transactional
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);

    private final PaymentDetailRepository paymentDetailRepository;

    public PaymentDetailServiceImpl(PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
    }

    @Override
    public PaymentDetail save(PaymentDetail paymentDetail) {
        log.debug("Request to save PaymentDetail : {}", paymentDetail);
        return paymentDetailRepository.save(paymentDetail);
    }

    @Override
    public PaymentDetail update(PaymentDetail paymentDetail) {
        log.debug("Request to update PaymentDetail : {}", paymentDetail);
        return paymentDetailRepository.save(paymentDetail);
    }

    @Override
    public Optional<PaymentDetail> partialUpdate(PaymentDetail paymentDetail) {
        log.debug("Request to partially update PaymentDetail : {}", paymentDetail);

        return paymentDetailRepository
            .findById(paymentDetail.getId())
            .map(existingPaymentDetail -> {
                if (paymentDetail.getComputedPaymentDetail() != null) {
                    existingPaymentDetail.setComputedPaymentDetail(paymentDetail.getComputedPaymentDetail());
                }

                return existingPaymentDetail;
            })
            .map(paymentDetailRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetail> findAll() {
        log.debug("Request to get all PaymentDetails");
        return paymentDetailRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDetail> findOne(UUID id) {
        log.debug("Request to get PaymentDetail : {}", id);
        return paymentDetailRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete PaymentDetail : {}", id);
        paymentDetailRepository.deleteById(id);
    }
}
