package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentParamRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentParamService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentParamDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.PaymentParamMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam}.
 */
@Service
@Transactional
public class PaymentParamServiceImpl implements PaymentParamService {

    private final Logger log = LoggerFactory.getLogger(PaymentParamServiceImpl.class);

    private final PaymentParamRepository paymentParamRepository;

    private final PaymentParamMapper paymentParamMapper;

    public PaymentParamServiceImpl(PaymentParamRepository paymentParamRepository, PaymentParamMapper paymentParamMapper) {
        this.paymentParamRepository = paymentParamRepository;
        this.paymentParamMapper = paymentParamMapper;
    }

    @Override
    public PaymentParamDTO save(PaymentParamDTO paymentParamDTO) {
        log.debug("Request to save PaymentParam : {}", paymentParamDTO);
        PaymentParam paymentParam = paymentParamMapper.toEntity(paymentParamDTO);
        paymentParam = paymentParamRepository.save(paymentParam);
        return paymentParamMapper.toDto(paymentParam);
    }

    @Override
    public PaymentParamDTO update(PaymentParamDTO paymentParamDTO) {
        log.debug("Request to update PaymentParam : {}", paymentParamDTO);
        PaymentParam paymentParam = paymentParamMapper.toEntity(paymentParamDTO);
        paymentParam = paymentParamRepository.save(paymentParam);
        return paymentParamMapper.toDto(paymentParam);
    }

    @Override
    public Optional<PaymentParamDTO> partialUpdate(PaymentParamDTO paymentParamDTO) {
        log.debug("Request to partially update PaymentParam : {}", paymentParamDTO);

        return paymentParamRepository
            .findById(paymentParamDTO.getId())
            .map(existingPaymentParam -> {
                paymentParamMapper.partialUpdate(existingPaymentParam, paymentParamDTO);

                return existingPaymentParam;
            })
            .map(paymentParamRepository::save)
            .map(paymentParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentParamDTO> findAll() {
        log.debug("Request to get all PaymentParams");
        return paymentParamRepository.findAll().stream().map(paymentParamMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentParamDTO> findOne(UUID id) {
        log.debug("Request to get PaymentParam : {}", id);
        return paymentParamRepository.findById(id).map(paymentParamMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete PaymentParam : {}", id);
        paymentParamRepository.deleteById(id);
    }
}