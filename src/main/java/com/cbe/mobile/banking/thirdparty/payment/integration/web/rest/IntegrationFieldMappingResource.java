package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.IntegrationFieldMappingRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.IntegrationFieldMappingService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping}.
 */
@RestController
@RequestMapping("/api/integration-field-mappings")
public class IntegrationFieldMappingResource {

    private final Logger log = LoggerFactory.getLogger(IntegrationFieldMappingResource.class);

    private static final String ENTITY_NAME = "integrationFieldMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegrationFieldMappingService integrationFieldMappingService;

    private final IntegrationFieldMappingRepository integrationFieldMappingRepository;

    public IntegrationFieldMappingResource(
        IntegrationFieldMappingService integrationFieldMappingService,
        IntegrationFieldMappingRepository integrationFieldMappingRepository
    ) {
        this.integrationFieldMappingService = integrationFieldMappingService;
        this.integrationFieldMappingRepository = integrationFieldMappingRepository;
    }

    /**
     * {@code POST  /integration-field-mappings} : Create a new integrationFieldMapping.
     *
     * @param integrationFieldMapping the integrationFieldMapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrationFieldMapping, or with status {@code 400 (Bad Request)} if the integrationFieldMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntegrationFieldMapping> createIntegrationFieldMapping(
        @RequestBody IntegrationFieldMapping integrationFieldMapping
    ) throws URISyntaxException {
        log.debug("REST request to save IntegrationFieldMapping : {}", integrationFieldMapping);
        if (integrationFieldMapping.getId() != null) {
            throw new BadRequestAlertException("A new integrationFieldMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        integrationFieldMapping = integrationFieldMappingService.save(integrationFieldMapping);
        return ResponseEntity.created(new URI("/api/integration-field-mappings/" + integrationFieldMapping.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, integrationFieldMapping.getId().toString()))
            .body(integrationFieldMapping);
    }

    /**
     * {@code PUT  /integration-field-mappings/:id} : Updates an existing integrationFieldMapping.
     *
     * @param id the id of the integrationFieldMapping to save.
     * @param integrationFieldMapping the integrationFieldMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationFieldMapping,
     * or with status {@code 400 (Bad Request)} if the integrationFieldMapping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrationFieldMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntegrationFieldMapping> updateIntegrationFieldMapping(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody IntegrationFieldMapping integrationFieldMapping
    ) throws URISyntaxException {
        log.debug("REST request to update IntegrationFieldMapping : {}, {}", id, integrationFieldMapping);
        if (integrationFieldMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrationFieldMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrationFieldMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        integrationFieldMapping = integrationFieldMappingService.update(integrationFieldMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, integrationFieldMapping.getId().toString()))
            .body(integrationFieldMapping);
    }

    /**
     * {@code PATCH  /integration-field-mappings/:id} : Partial updates given fields of an existing integrationFieldMapping, field will ignore if it is null
     *
     * @param id the id of the integrationFieldMapping to save.
     * @param integrationFieldMapping the integrationFieldMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationFieldMapping,
     * or with status {@code 400 (Bad Request)} if the integrationFieldMapping is not valid,
     * or with status {@code 404 (Not Found)} if the integrationFieldMapping is not found,
     * or with status {@code 500 (Internal Server Error)} if the integrationFieldMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntegrationFieldMapping> partialUpdateIntegrationFieldMapping(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody IntegrationFieldMapping integrationFieldMapping
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntegrationFieldMapping partially : {}, {}", id, integrationFieldMapping);
        if (integrationFieldMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrationFieldMapping.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrationFieldMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntegrationFieldMapping> result = integrationFieldMappingService.partialUpdate(integrationFieldMapping);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, integrationFieldMapping.getId().toString())
        );
    }

    /**
     * {@code GET  /integration-field-mappings} : get all the integrationFieldMappings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrationFieldMappings in body.
     */
    @GetMapping("")
    public List<IntegrationFieldMapping> getAllIntegrationFieldMappings() {
        log.debug("REST request to get all IntegrationFieldMappings");
        return integrationFieldMappingService.findAll();
    }

    /**
     * {@code GET  /integration-field-mappings/:id} : get the "id" integrationFieldMapping.
     *
     * @param id the id of the integrationFieldMapping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrationFieldMapping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntegrationFieldMapping> getIntegrationFieldMapping(@PathVariable("id") UUID id) {
        log.debug("REST request to get IntegrationFieldMapping : {}", id);
        Optional<IntegrationFieldMapping> integrationFieldMapping = integrationFieldMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrationFieldMapping);
    }

    /**
     * {@code DELETE  /integration-field-mappings/:id} : delete the "id" integrationFieldMapping.
     *
     * @param id the id of the integrationFieldMapping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntegrationFieldMapping(@PathVariable("id") UUID id) {
        log.debug("REST request to delete IntegrationFieldMapping : {}", id);
        integrationFieldMappingService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
