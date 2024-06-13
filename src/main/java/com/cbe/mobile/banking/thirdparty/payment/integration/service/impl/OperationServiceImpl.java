package com.cbe.mobile.banking.thirdparty.payment.integration.service.impl;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.OperationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.OperationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation}.
 */
@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationServiceImpl.class);

    private final OperationRepository operationRepository;

    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation save(Operation operation) {
        log.debug("Request to save Operation : {}", operation);
        return operationRepository.save(operation);
    }

    @Override
    public Operation update(Operation operation) {
        log.debug("Request to update Operation : {}", operation);
        return operationRepository.save(operation);
    }

    @Override
    public Optional<Operation> partialUpdate(Operation operation) {
        log.debug("Request to partially update Operation : {}", operation);

        return operationRepository
            .findById(operation.getId())
            .map(existingOperation -> {
                if (operation.getOperationName() != null) {
                    existingOperation.setOperationName(operation.getOperationName());
                }
                if (operation.getHttpMethod() != null) {
                    existingOperation.setHttpMethod(operation.getHttpMethod());
                }
                if (operation.getEndpointPath() != null) {
                    existingOperation.setEndpointPath(operation.getEndpointPath());
                }
                if (operation.getRequestBodyTemplate() != null) {
                    existingOperation.setRequestBodyTemplate(operation.getRequestBodyTemplate());
                }

                return existingOperation;
            })
            .map(operationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Operation> findAll() {
        log.debug("Request to get all Operations");
        return operationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Operation> findOne(UUID id) {
        log.debug("Request to get Operation : {}", id);
        return operationRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.deleteById(id);
    }
}
