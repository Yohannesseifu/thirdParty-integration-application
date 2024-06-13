package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentParamRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentParamService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam}.
 */
@Service
@Transactional
public class PaymentParamServiceImpl implements PaymentParamService {

    private final Logger log = LoggerFactory.getLogger(PaymentParamServiceImpl.class);

    private final PaymentParamRepository paymentParamRepository;

    public PaymentParamServiceImpl(PaymentParamRepository paymentParamRepository) {
        this.paymentParamRepository = paymentParamRepository;
    }

    @Override
    public PaymentParam save(PaymentParam paymentParam) {
        log.debug("Request to save PaymentParam : {}", paymentParam);
        return paymentParamRepository.save(paymentParam);
    }

    @Override
    public PaymentParam update(PaymentParam paymentParam) {
        log.debug("Request to update PaymentParam : {}", paymentParam);
        return paymentParamRepository.save(paymentParam);
    }

    @Override
    public Optional<PaymentParam> partialUpdate(PaymentParam paymentParam) {
        log.debug("Request to partially update PaymentParam : {}", paymentParam);

        return paymentParamRepository
            .findById(paymentParam.getId())
            .map(existingPaymentParam -> {
                if (paymentParam.getType() != null) {
                    existingPaymentParam.setType(paymentParam.getType());
                }
                if (paymentParam.getName() != null) {
                    existingPaymentParam.setName(paymentParam.getName());
                }
                if (paymentParam.getValueStr() != null) {
                    existingPaymentParam.setValueStr(paymentParam.getValueStr());
                }
                if (paymentParam.getDataType() != null) {
                    existingPaymentParam.setDataType(paymentParam.getDataType());
                }

                return existingPaymentParam;
            })
            .map(paymentParamRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentParam> findAll() {
        log.debug("Request to get all PaymentParams");
        return paymentParamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentParam> findOne(UUID id) {
        log.debug("Request to get PaymentParam : {}", id);
        return paymentParamRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete PaymentParam : {}", id);
        paymentParamRepository.deleteById(id);
    }
}
