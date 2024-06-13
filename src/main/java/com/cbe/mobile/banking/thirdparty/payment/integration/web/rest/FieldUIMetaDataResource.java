package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldUIMetaDataRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FieldUIMetaDataService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData}.
 */
@RestController
@RequestMapping("/api/field-ui-meta-data")
public class FieldUIMetaDataResource {

    private final Logger log = LoggerFactory.getLogger(FieldUIMetaDataResource.class);

    private static final String ENTITY_NAME = "fieldUIMetaData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldUIMetaDataService fieldUIMetaDataService;

    private final FieldUIMetaDataRepository fieldUIMetaDataRepository;

    public FieldUIMetaDataResource(FieldUIMetaDataService fieldUIMetaDataService, FieldUIMetaDataRepository fieldUIMetaDataRepository) {
        this.fieldUIMetaDataService = fieldUIMetaDataService;
        this.fieldUIMetaDataRepository = fieldUIMetaDataRepository;
    }

    /**
     * {@code POST  /field-ui-meta-data} : Create a new fieldUIMetaData.
     *
     * @param fieldUIMetaData the fieldUIMetaData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldUIMetaData, or with status {@code 400 (Bad Request)} if the fieldUIMetaData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FieldUIMetaData> createFieldUIMetaData(@RequestBody FieldUIMetaData fieldUIMetaData) throws URISyntaxException {
        log.debug("REST request to save FieldUIMetaData : {}", fieldUIMetaData);
        if (fieldUIMetaData.getId() != null) {
            throw new BadRequestAlertException("A new fieldUIMetaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fieldUIMetaData = fieldUIMetaDataService.save(fieldUIMetaData);
        return ResponseEntity.created(new URI("/api/field-ui-meta-data/" + fieldUIMetaData.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fieldUIMetaData.getId().toString()))
            .body(fieldUIMetaData);
    }

    /**
     * {@code PUT  /field-ui-meta-data/:id} : Updates an existing fieldUIMetaData.
     *
     * @param id the id of the fieldUIMetaData to save.
     * @param fieldUIMetaData the fieldUIMetaData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldUIMetaData,
     * or with status {@code 400 (Bad Request)} if the fieldUIMetaData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldUIMetaData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldUIMetaData> updateFieldUIMetaData(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FieldUIMetaData fieldUIMetaData
    ) throws URISyntaxException {
        log.debug("REST request to update FieldUIMetaData : {}, {}", id, fieldUIMetaData);
        if (fieldUIMetaData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldUIMetaData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldUIMetaDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fieldUIMetaData = fieldUIMetaDataService.update(fieldUIMetaData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldUIMetaData.getId().toString()))
            .body(fieldUIMetaData);
    }

    /**
     * {@code PATCH  /field-ui-meta-data/:id} : Partial updates given fields of an existing fieldUIMetaData, field will ignore if it is null
     *
     * @param id the id of the fieldUIMetaData to save.
     * @param fieldUIMetaData the fieldUIMetaData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldUIMetaData,
     * or with status {@code 400 (Bad Request)} if the fieldUIMetaData is not valid,
     * or with status {@code 404 (Not Found)} if the fieldUIMetaData is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldUIMetaData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldUIMetaData> partialUpdateFieldUIMetaData(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody FieldUIMetaData fieldUIMetaData
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldUIMetaData partially : {}, {}", id, fieldUIMetaData);
        if (fieldUIMetaData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldUIMetaData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldUIMetaDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldUIMetaData> result = fieldUIMetaDataService.partialUpdate(fieldUIMetaData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldUIMetaData.getId().toString())
        );
    }

    /**
     * {@code GET  /field-ui-meta-data} : get all the fieldUIMetaData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldUIMetaData in body.
     */
    @GetMapping("")
    public List<FieldUIMetaData> getAllFieldUIMetaData() {
        log.debug("REST request to get all FieldUIMetaData");
        return fieldUIMetaDataService.findAll();
    }

    /**
     * {@code GET  /field-ui-meta-data/:id} : get the "id" fieldUIMetaData.
     *
     * @param id the id of the fieldUIMetaData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldUIMetaData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldUIMetaData> getFieldUIMetaData(@PathVariable("id") UUID id) {
        log.debug("REST request to get FieldUIMetaData : {}", id);
        Optional<FieldUIMetaData> fieldUIMetaData = fieldUIMetaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldUIMetaData);
    }

    /**
     * {@code DELETE  /field-ui-meta-data/:id} : delete the "id" fieldUIMetaData.
     *
     * @param id the id of the fieldUIMetaData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFieldUIMetaData(@PathVariable("id") UUID id) {
        log.debug("REST request to delete FieldUIMetaData : {}", id);
        fieldUIMetaDataService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
