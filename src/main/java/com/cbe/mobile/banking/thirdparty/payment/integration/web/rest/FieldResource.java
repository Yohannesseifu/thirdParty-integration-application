package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FieldRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FieldService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field}.
 */
@RestController
@RequestMapping("/api/fields")
public class FieldResource {

    private final Logger log = LoggerFactory.getLogger(FieldResource.class);

    private static final String ENTITY_NAME = "field";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldService fieldService;

    private final FieldRepository fieldRepository;

    public FieldResource(FieldService fieldService, FieldRepository fieldRepository) {
        this.fieldService = fieldService;
        this.fieldRepository = fieldRepository;
    }

    /**
     * {@code POST  /fields} : Create a new field.
     *
     * @param field the field to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new field, or with status {@code 400 (Bad Request)} if the field has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Field> createField(@RequestBody Field field) throws URISyntaxException {
        log.debug("REST request to save Field : {}", field);
        if (field.getId() != null) {
            throw new BadRequestAlertException("A new field cannot already have an ID", ENTITY_NAME, "idexists");
        }
        field = fieldService.save(field);
        return ResponseEntity.created(new URI("/api/fields/" + field.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, field.getId().toString()))
            .body(field);
    }

    /**
     * {@code PUT  /fields/:id} : Updates an existing field.
     *
     * @param id the id of the field to save.
     * @param field the field to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated field,
     * or with status {@code 400 (Bad Request)} if the field is not valid,
     * or with status {@code 500 (Internal Server Error)} if the field couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Field> updateField(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Field field)
        throws URISyntaxException {
        log.debug("REST request to update Field : {}, {}", id, field);
        if (field.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, field.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        field = fieldService.update(field);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, field.getId().toString()))
            .body(field);
    }

    /**
     * {@code PATCH  /fields/:id} : Partial updates given fields of an existing field, field will ignore if it is null
     *
     * @param id the id of the field to save.
     * @param field the field to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated field,
     * or with status {@code 400 (Bad Request)} if the field is not valid,
     * or with status {@code 404 (Not Found)} if the field is not found,
     * or with status {@code 500 (Internal Server Error)} if the field couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Field> partialUpdateField(@PathVariable(value = "id", required = false) final UUID id, @RequestBody Field field)
        throws URISyntaxException {
        log.debug("REST request to partial update Field partially : {}, {}", id, field);
        if (field.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, field.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Field> result = fieldService.partialUpdate(field);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, field.getId().toString())
        );
    }

    /**
     * {@code GET  /fields} : get all the fields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fields in body.
     */
    @GetMapping("")
    public List<Field> getAllFields() {
        log.debug("REST request to get all Fields");
        return fieldService.findAll();
    }

    /**
     * {@code GET  /fields/:id} : get the "id" field.
     *
     * @param id the id of the field to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the field, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Field> getField(@PathVariable("id") UUID id) {
        log.debug("REST request to get Field : {}", id);
        Optional<Field> field = fieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(field);
    }

    /**
     * {@code DELETE  /fields/:id} : delete the "id" field.
     *
     * @param id the id of the field to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Field : {}", id);
        fieldService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
