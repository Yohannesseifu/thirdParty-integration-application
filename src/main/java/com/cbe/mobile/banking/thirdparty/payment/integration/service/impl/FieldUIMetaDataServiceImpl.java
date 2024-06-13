package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldUIMetaDataRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FieldUIMetaDataService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData}.
 */
@Service
@Transactional
public class FieldUIMetaDataServiceImpl implements FieldUIMetaDataService {

    private final Logger log = LoggerFactory.getLogger(FieldUIMetaDataServiceImpl.class);

    private final FieldUIMetaDataRepository fieldUIMetaDataRepository;

    public FieldUIMetaDataServiceImpl(FieldUIMetaDataRepository fieldUIMetaDataRepository) {
        this.fieldUIMetaDataRepository = fieldUIMetaDataRepository;
    }

    @Override
    public FieldUIMetaData save(FieldUIMetaData fieldUIMetaData) {
        log.debug("Request to save FieldUIMetaData : {}", fieldUIMetaData);
        return fieldUIMetaDataRepository.save(fieldUIMetaData);
    }

    @Override
    public FieldUIMetaData update(FieldUIMetaData fieldUIMetaData) {
        log.debug("Request to update FieldUIMetaData : {}", fieldUIMetaData);
        return fieldUIMetaDataRepository.save(fieldUIMetaData);
    }

    @Override
    public Optional<FieldUIMetaData> partialUpdate(FieldUIMetaData fieldUIMetaData) {
        log.debug("Request to partially update FieldUIMetaData : {}", fieldUIMetaData);

        return fieldUIMetaDataRepository
            .findById(fieldUIMetaData.getId())
            .map(existingFieldUIMetaData -> {
                if (fieldUIMetaData.getLabel() != null) {
                    existingFieldUIMetaData.setLabel(fieldUIMetaData.getLabel());
                }
                if (fieldUIMetaData.getInputInterface() != null) {
                    existingFieldUIMetaData.setInputInterface(fieldUIMetaData.getInputInterface());
                }
                if (fieldUIMetaData.getWidth() != null) {
                    existingFieldUIMetaData.setWidth(fieldUIMetaData.getWidth());
                }
                if (fieldUIMetaData.getNote() != null) {
                    existingFieldUIMetaData.setNote(fieldUIMetaData.getNote());
                }
                if (fieldUIMetaData.getValidationPattern() != null) {
                    existingFieldUIMetaData.setValidationPattern(fieldUIMetaData.getValidationPattern());
                }
                if (fieldUIMetaData.getOptions() != null) {
                    existingFieldUIMetaData.setOptions(fieldUIMetaData.getOptions());
                }
                if (fieldUIMetaData.getDisplayOptions() != null) {
                    existingFieldUIMetaData.setDisplayOptions(fieldUIMetaData.getDisplayOptions());
                }
                if (fieldUIMetaData.getConditions() != null) {
                    existingFieldUIMetaData.setConditions(fieldUIMetaData.getConditions());
                }
                if (fieldUIMetaData.getTranslations() != null) {
                    existingFieldUIMetaData.setTranslations(fieldUIMetaData.getTranslations());
                }

                return existingFieldUIMetaData;
            })
            .map(fieldUIMetaDataRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldUIMetaData> findAll() {
        log.debug("Request to get all FieldUIMetaData");
        return fieldUIMetaDataRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldUIMetaData> findOne(UUID id) {
        log.debug("Request to get FieldUIMetaData : {}", id);
        return fieldUIMetaDataRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete FieldUIMetaData : {}", id);
        fieldUIMetaDataRepository.deleteById(id);
    }
}
