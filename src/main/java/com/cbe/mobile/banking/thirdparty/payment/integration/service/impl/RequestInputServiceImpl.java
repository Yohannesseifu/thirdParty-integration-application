package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.RequestInputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.RequestInputService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput}.
 */
@Service
@Transactional
public class RequestInputServiceImpl implements RequestInputService {

    private final Logger log = LoggerFactory.getLogger(RequestInputServiceImpl.class);

    private final RequestInputRepository requestInputRepository;

    public RequestInputServiceImpl(RequestInputRepository requestInputRepository) {
        this.requestInputRepository = requestInputRepository;
    }

    @Override
    public RequestInput save(RequestInput requestInput) {
        log.debug("Request to save RequestInput : {}", requestInput);
        return requestInputRepository.save(requestInput);
    }

    @Override
    public RequestInput update(RequestInput requestInput) {
        log.debug("Request to update RequestInput : {}", requestInput);
        return requestInputRepository.save(requestInput);
    }

    @Override
    public Optional<RequestInput> partialUpdate(RequestInput requestInput) {
        log.debug("Request to partially update RequestInput : {}", requestInput);

        return requestInputRepository
            .findById(requestInput.getId())
            .map(existingRequestInput -> {
                if (requestInput.getInputName() != null) {
                    existingRequestInput.setInputName(requestInput.getInputName());
                }
                if (requestInput.getInputType() != null) {
                    existingRequestInput.setInputType(requestInput.getInputType());
                }
                if (requestInput.getDataType() != null) {
                    existingRequestInput.setDataType(requestInput.getDataType());
                }
                if (requestInput.getTestValue() != null) {
                    existingRequestInput.setTestValue(requestInput.getTestValue());
                }
                if (requestInput.getDefaultValue() != null) {
                    existingRequestInput.setDefaultValue(requestInput.getDefaultValue());
                }
                if (requestInput.getValueSource() != null) {
                    existingRequestInput.setValueSource(requestInput.getValueSource());
                }
                if (requestInput.getIsEncoded() != null) {
                    existingRequestInput.setIsEncoded(requestInput.getIsEncoded());
                }
                if (requestInput.getMaxLength() != null) {
                    existingRequestInput.setMaxLength(requestInput.getMaxLength());
                }
                if (requestInput.getMinLength() != null) {
                    existingRequestInput.setMinLength(requestInput.getMinLength());
                }
                if (requestInput.getMinValue() != null) {
                    existingRequestInput.setMinValue(requestInput.getMinValue());
                }
                if (requestInput.getMaxValue() != null) {
                    existingRequestInput.setMaxValue(requestInput.getMaxValue());
                }
                if (requestInput.getValidationPattern() != null) {
                    existingRequestInput.setValidationPattern(requestInput.getValidationPattern());
                }
                if (requestInput.getIsRequired() != null) {
                    existingRequestInput.setIsRequired(requestInput.getIsRequired());
                }

                return existingRequestInput;
            })
            .map(requestInputRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestInput> findAll() {
        log.debug("Request to get all RequestInputs");
        return requestInputRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestInput> findOne(UUID id) {
        log.debug("Request to get RequestInput : {}", id);
        return requestInputRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete RequestInput : {}", id);
        requestInputRepository.deleteById(id);
    }
}
