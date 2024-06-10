package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FormUiRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FormUiService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FormUiDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.FormUiMapper;
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
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi}.
 */
@Service
@Transactional
public class FormUiServiceImpl implements FormUiService {

    private final Logger log = LoggerFactory.getLogger(FormUiServiceImpl.class);

    private final FormUiRepository formUiRepository;

    private final FormUiMapper formUiMapper;

    public FormUiServiceImpl(FormUiRepository formUiRepository, FormUiMapper formUiMapper) {
        this.formUiRepository = formUiRepository;
        this.formUiMapper = formUiMapper;
    }

    @Override
    public FormUiDTO save(FormUiDTO formUiDTO) {
        log.debug("Request to save FormUi : {}", formUiDTO);
        FormUi formUi = formUiMapper.toEntity(formUiDTO);
        formUi = formUiRepository.save(formUi);
        return formUiMapper.toDto(formUi);
    }

    @Override
    public FormUiDTO update(FormUiDTO formUiDTO) {
        log.debug("Request to update FormUi : {}", formUiDTO);
        FormUi formUi = formUiMapper.toEntity(formUiDTO);
        formUi = formUiRepository.save(formUi);
        return formUiMapper.toDto(formUi);
    }

    @Override
    public Optional<FormUiDTO> partialUpdate(FormUiDTO formUiDTO) {
        log.debug("Request to partially update FormUi : {}", formUiDTO);

        return formUiRepository
            .findById(formUiDTO.getId())
            .map(existingFormUi -> {
                formUiMapper.partialUpdate(existingFormUi, formUiDTO);

                return existingFormUi;
            })
            .map(formUiRepository::save)
            .map(formUiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormUiDTO> findAll() {
        log.debug("Request to get all FormUis");
        return formUiRepository.findAll().stream().map(formUiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormUiDTO> findOne(UUID id) {
        log.debug("Request to get FormUi : {}", id);
        return formUiRepository.findById(id).map(formUiMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete FormUi : {}", id);
        formUiRepository.deleteById(id);
    }
}
