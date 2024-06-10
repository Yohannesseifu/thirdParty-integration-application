package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentDetailRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentDetailService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentDetailDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.PaymentDetailMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail}.
 */
@Service
@Transactional
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);

    private final PaymentDetailRepository paymentDetailRepository;

    private final PaymentDetailMapper paymentDetailMapper;

    public PaymentDetailServiceImpl(PaymentDetailRepository paymentDetailRepository, PaymentDetailMapper paymentDetailMapper) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.paymentDetailMapper = paymentDetailMapper;
    }

    @Override
    public PaymentDetailDTO save(PaymentDetailDTO paymentDetailDTO) {
        log.debug("Request to save PaymentDetail : {}", paymentDetailDTO);
        PaymentDetail paymentDetail = paymentDetailMapper.toEntity(paymentDetailDTO);
        paymentDetail = paymentDetailRepository.save(paymentDetail);
        return paymentDetailMapper.toDto(paymentDetail);
    }

    @Override
    public PaymentDetailDTO update(PaymentDetailDTO paymentDetailDTO) {
        log.debug("Request to update PaymentDetail : {}", paymentDetailDTO);
        PaymentDetail paymentDetail = paymentDetailMapper.toEntity(paymentDetailDTO);
        paymentDetail = paymentDetailRepository.save(paymentDetail);
        return paymentDetailMapper.toDto(paymentDetail);
    }

    @Override
    public Optional<PaymentDetailDTO> partialUpdate(PaymentDetailDTO paymentDetailDTO) {
        log.debug("Request to partially update PaymentDetail : {}", paymentDetailDTO);

        return paymentDetailRepository
            .findById(paymentDetailDTO.getId())
            .map(existingPaymentDetail -> {
                paymentDetailMapper.partialUpdate(existingPaymentDetail, paymentDetailDTO);

                return existingPaymentDetail;
            })
            .map(paymentDetailRepository::save)
            .map(paymentDetailMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDetailDTO> findAll() {
        log.debug("Request to get all PaymentDetails");
        return paymentDetailRepository.findAll().stream().map(paymentDetailMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentDetailDTO> findOne(UUID id) {
        log.debug("Request to get PaymentDetail : {}", id);
        return paymentDetailRepository.findById(id).map(paymentDetailMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete PaymentDetail : {}", id);
        paymentDetailRepository.deleteById(id);
    }
}
