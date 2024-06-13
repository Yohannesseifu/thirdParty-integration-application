package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FormUiRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FormUiService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi}.
 */
@Service
@Transactional
public class FormUiServiceImpl implements FormUiService {

    private final Logger log = LoggerFactory.getLogger(FormUiServiceImpl.class);

    private final FormUiRepository formUiRepository;

    public FormUiServiceImpl(FormUiRepository formUiRepository) {
        this.formUiRepository = formUiRepository;
    }

    @Override
    public FormUi save(FormUi formUi) {
        log.debug("Request to save FormUi : {}", formUi);
        return formUiRepository.save(formUi);
    }

    @Override
    public FormUi update(FormUi formUi) {
        log.debug("Request to update FormUi : {}", formUi);
        return formUiRepository.save(formUi);
    }

    @Override
    public Optional<FormUi> partialUpdate(FormUi formUi) {
        log.debug("Request to partially update FormUi : {}", formUi);

        return formUiRepository
            .findById(formUi.getId())
            .map(existingFormUi -> {
                if (formUi.getFormName() != null) {
                    existingFormUi.setFormName(formUi.getFormName());
                }
                if (formUi.getFormDescription() != null) {
                    existingFormUi.setFormDescription(formUi.getFormDescription());
                }
                if (formUi.getFormType() != null) {
                    existingFormUi.setFormType(formUi.getFormType());
                }
                if (formUi.getStepOrder() != null) {
                    existingFormUi.setStepOrder(formUi.getStepOrder());
                }

                return existingFormUi;
            })
            .map(formUiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormUi> findAll() {
        log.debug("Request to get all FormUis");
        return formUiRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormUi> findOne(UUID id) {
        log.debug("Request to get FormUi : {}", id);
        return formUiRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete FormUi : {}", id);
        formUiRepository.deleteById(id);
    }
}
