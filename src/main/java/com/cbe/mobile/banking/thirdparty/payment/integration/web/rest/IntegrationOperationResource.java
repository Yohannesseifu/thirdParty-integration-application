package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationOperationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.IntegrationOperationService;
import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation}.
 */
@RestController
@RequestMapping("/api/integration-operations")
public class IntegrationOperationResource {

    private final Logger log = LoggerFactory.getLogger(IntegrationOperationResource.class);

    private static final String ENTITY_NAME = "integrationOperation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegrationOperationService integrationOperationService;

    private final IntegrationOperationRepository integrationOperationRepository;

    public IntegrationOperationResource(
        IntegrationOperationService integrationOperationService,
        IntegrationOperationRepository integrationOperationRepository
    ) {
        this.integrationOperationService = integrationOperationService;
        this.integrationOperationRepository = integrationOperationRepository;
    }

    /**
     * {@code POST  /integration-operations} : Create a new integrationOperation.
     *
     * @param integrationOperation the integrationOperation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrationOperation, or with status {@code 400 (Bad Request)} if the integrationOperation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntegrationOperation> createIntegrationOperation(@RequestBody IntegrationOperation integrationOperation)
        throws URISyntaxException {
        log.debug("REST request to save IntegrationOperation : {}", integrationOperation);
        if (integrationOperation.getId() != null) {
            throw new BadRequestAlertException("A new integrationOperation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        integrationOperation = integrationOperationService.save(integrationOperation);
        return ResponseEntity.created(new URI("/api/integration-operations/" + integrationOperation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, integrationOperation.getId().toString()))
            .body(integrationOperation);
    }

    /**
     * {@code PUT  /integration-operations/:id} : Updates an existing integrationOperation.
     *
     * @param id the id of the integrationOperation to save.
     * @param integrationOperation the integrationOperation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationOperation,
     * or with status {@code 400 (Bad Request)} if the integrationOperation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrationOperation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntegrationOperation> updateIntegrationOperation(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody IntegrationOperation integrationOperation
    ) throws URISyntaxException {
        log.debug("REST request to update IntegrationOperation : {}, {}", id, integrationOperation);
        if (integrationOperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrationOperation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrationOperationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        integrationOperation = integrationOperationService.update(integrationOperation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, integrationOperation.getId().toString()))
            .body(integrationOperation);
    }

    /**
     * {@code PATCH  /integration-operations/:id} : Partial updates given fields of an existing integrationOperation, field will ignore if it is null
     *
     * @param id the id of the integrationOperation to save.
     * @param integrationOperation the integrationOperation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationOperation,
     * or with status {@code 400 (Bad Request)} if the integrationOperation is not valid,
     * or with status {@code 404 (Not Found)} if the integrationOperation is not found,
     * or with status {@code 500 (Internal Server Error)} if the integrationOperation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntegrationOperation> partialUpdateIntegrationOperation(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody IntegrationOperation integrationOperation
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntegrationOperation partially : {}, {}", id, integrationOperation);
        if (integrationOperation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrationOperation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrationOperationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntegrationOperation> result = integrationOperationService.partialUpdate(integrationOperation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, integrationOperation.getId().toString())
        );
    }

    /**
     * {@code GET  /integration-operations} : get all the integrationOperations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrationOperations in body.
     */
    @GetMapping("")
    public List<IntegrationOperation> getAllIntegrationOperations() {
        log.debug("REST request to get all IntegrationOperations");
        return integrationOperationService.findAll();
    }

    /**
     * {@code GET  /integration-operations/:id} : get the "id" integrationOperation.
     *
     * @param id the id of the integrationOperation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrationOperation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntegrationOperation> getIntegrationOperation(@PathVariable("id") UUID id) {
        log.debug("REST request to get IntegrationOperation : {}", id);
        Optional<IntegrationOperation> integrationOperation = integrationOperationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrationOperation);
    }

    /**
     * {@code DELETE  /integration-operations/:id} : delete the "id" integrationOperation.
     *
     * @param id the id of the integrationOperation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntegrationOperation(@PathVariable("id") UUID id) {
        log.debug("REST request to delete IntegrationOperation : {}", id);
        integrationOperationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
