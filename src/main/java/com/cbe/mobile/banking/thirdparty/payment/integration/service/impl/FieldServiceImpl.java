package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FieldService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field}.
 */
@Service
@Transactional
public class FieldServiceImpl implements FieldService {

    private final Logger log = LoggerFactory.getLogger(FieldServiceImpl.class);

    private final FieldRepository fieldRepository;

    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Field save(Field field) {
        log.debug("Request to save Field : {}", field);
        return fieldRepository.save(field);
    }

    @Override
    public Field update(Field field) {
        log.debug("Request to update Field : {}", field);
        return fieldRepository.save(field);
    }

    @Override
    public Optional<Field> partialUpdate(Field field) {
        log.debug("Request to partially update Field : {}", field);

        return fieldRepository
            .findById(field.getId())
            .map(existingField -> {
                if (field.getName() != null) {
                    existingField.setName(field.getName());
                }
                if (field.getDataType() != null) {
                    existingField.setDataType(field.getDataType());
                }
                if (field.getIsUnique() != null) {
                    existingField.setIsUnique(field.getIsUnique());
                }
                if (field.getMaxLength() != null) {
                    existingField.setMaxLength(field.getMaxLength());
                }
                if (field.getMinLength() != null) {
                    existingField.setMinLength(field.getMinLength());
                }
                if (field.getMinValue() != null) {
                    existingField.setMinValue(field.getMinValue());
                }
                if (field.getMaxValue() != null) {
                    existingField.setMaxValue(field.getMaxValue());
                }
                if (field.getIsRequired() != null) {
                    existingField.setIsRequired(field.getIsRequired());
                }
                if (field.getTransferCoreMapping() != null) {
                    existingField.setTransferCoreMapping(field.getTransferCoreMapping());
                }
                if (field.getSortOrder() != null) {
                    existingField.setSortOrder(field.getSortOrder());
                }

                return existingField;
            })
            .map(fieldRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Field> findAll() {
        log.debug("Request to get all Fields");
        return fieldRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Field> findOne(UUID id) {
        log.debug("Request to get Field : {}", id);
        return fieldRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Field : {}", id);
        fieldRepository.deleteById(id);
    }
}
