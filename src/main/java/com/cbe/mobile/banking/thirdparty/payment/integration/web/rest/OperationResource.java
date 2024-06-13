package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.OperationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.OperationService;
import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation}.
 */
@RestController
@RequestMapping("/api/operations")
public class OperationResource {

    private final Logger log = LoggerFactory.getLogger(OperationResource.class);

    private static final String ENTITY_NAME = "operation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationService operationService;

    private final OperationRepository operationRepository;

    public OperationResource(OperationService operationService, OperationRepository operationRepository) {
        this.operationService = operationService;
        this.operationRepository = operationRepository;
    }

    /**
     * {@code POST  /operations} : Create a new operation.
     *
     * @param operation the operation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operation, or with status {@code 400 (Bad Request)} if the operation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Operation> createOperation(@Valid @RequestBody Operation operation) throws URISyntaxException {
        log.debug("REST request to save Operation : {}", operation);
        if (operation.getId() != null) {
            throw new BadRequestAlertException("A new operation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        operation = operationService.save(operation);
        return ResponseEntity.created(new URI("/api/operations/" + operation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, operation.getId().toString()))
            .body(operation);
    }

    /**
     * {@code PUT  /operations/:id} : Updates an existing operation.
     *
     * @param id the id of the operation to save.
     * @param operation the operation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operation,
     * or with status {@code 400 (Bad Request)} if the operation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Operation> updateOperation(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Operation operation
    ) throws URISyntaxException {
        log.debug("REST request to update Operation : {}, {}", id, operation);
        if (operation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        operation = operationService.update(operation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operation.getId().toString()))
            .body(operation);
    }

    /**
     * {@code PATCH  /operations/:id} : Partial updates given fields of an existing operation, field will ignore if it is null
     *
     * @param id the id of the operation to save.
     * @param operation the operation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operation,
     * or with status {@code 400 (Bad Request)} if the operation is not valid,
     * or with status {@code 404 (Not Found)} if the operation is not found,
     * or with status {@code 500 (Internal Server Error)} if the operation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Operation> partialUpdateOperation(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Operation operation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Operation partially : {}, {}", id, operation);
        if (operation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Operation> result = operationService.partialUpdate(operation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operation.getId().toString())
        );
    }

    /**
     * {@code GET  /operations} : get all the operations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operations in body.
     */
    @GetMapping("")
    public List<Operation> getAllOperations() {
        log.debug("REST request to get all Operations");
        return operationService.findAll();
    }

    /**
     * {@code GET  /operations/:id} : get the "id" operation.
     *
     * @param id the id of the operation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Operation> getOperation(@PathVariable("id") UUID id) {
        log.debug("REST request to get Operation : {}", id);
        Optional<Operation> operation = operationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operation);
    }

    /**
     * {@code DELETE  /operations/:id} : delete the "id" operation.
     *
     * @param id the id of the operation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperation(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Operation : {}", id);
        operationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
