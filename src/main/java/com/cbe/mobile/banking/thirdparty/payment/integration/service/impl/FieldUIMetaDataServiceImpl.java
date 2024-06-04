package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldUIMetaDataRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FieldUIMetaDataService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldUIMetaDataDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper.FieldUIMetaDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final FieldUIMetaDataMapper fieldUIMetaDataMapper;

    public FieldUIMetaDataServiceImpl(FieldUIMetaDataRepository fieldUIMetaDataRepository, FieldUIMetaDataMapper fieldUIMetaDataMapper) {
        this.fieldUIMetaDataRepository = fieldUIMetaDataRepository;
        this.fieldUIMetaDataMapper = fieldUIMetaDataMapper;
    }

    @Override
    public FieldUIMetaDataDTO save(FieldUIMetaDataDTO fieldUIMetaDataDTO) {
        log.debug("Request to save FieldUIMetaData : {}", fieldUIMetaDataDTO);
        FieldUIMetaData fieldUIMetaData = fieldUIMetaDataMapper.toEntity(fieldUIMetaDataDTO);
        fieldUIMetaData = fieldUIMetaDataRepository.save(fieldUIMetaData);
        return fieldUIMetaDataMapper.toDto(fieldUIMetaData);
    }

    @Override
    public FieldUIMetaDataDTO update(FieldUIMetaDataDTO fieldUIMetaDataDTO) {
        log.debug("Request to update FieldUIMetaData : {}", fieldUIMetaDataDTO);
        FieldUIMetaData fieldUIMetaData = fieldUIMetaDataMapper.toEntity(fieldUIMetaDataDTO);
        fieldUIMetaData = fieldUIMetaDataRepository.save(fieldUIMetaData);
        return fieldUIMetaDataMapper.toDto(fieldUIMetaData);
    }

    @Override
    public Optional<FieldUIMetaDataDTO> partialUpdate(FieldUIMetaDataDTO fieldUIMetaDataDTO) {
        log.debug("Request to partially update FieldUIMetaData : {}", fieldUIMetaDataDTO);

        return fieldUIMetaDataRepository
            .findById(fieldUIMetaDataDTO.getId())
            .map(existingFieldUIMetaData -> {
                fieldUIMetaDataMapper.partialUpdate(existingFieldUIMetaData, fieldUIMetaDataDTO);

                return existingFieldUIMetaData;
            })
            .map(fieldUIMetaDataRepository::save)
            .map(fieldUIMetaDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldUIMetaDataDTO> findAll() {
        log.debug("Request to get all FieldUIMetaData");
        return fieldUIMetaDataRepository
            .findAll()
            .stream()
            .map(fieldUIMetaDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldUIMetaDataDTO> findOne(Long id) {
        log.debug("Request to get FieldUIMetaData : {}", id);
        return fieldUIMetaDataRepository.findById(id).map(fieldUIMetaDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldUIMetaData : {}", id);
        fieldUIMetaDataRepository.deleteById(id);
    }
}
