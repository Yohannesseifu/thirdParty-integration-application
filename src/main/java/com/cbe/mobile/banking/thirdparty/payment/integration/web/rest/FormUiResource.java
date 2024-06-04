package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.repository.FormUiRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.FormUiService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FormUiDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi}.
 */
@RestController
@RequestMapping("/api/form-uis")
public class FormUiResource {

    private final Logger log = LoggerFactory.getLogger(FormUiResource.class);

    private static final String ENTITY_NAME = "formUi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormUiService formUiService;

    private final FormUiRepository formUiRepository;

    public FormUiResource(FormUiService formUiService, FormUiRepository formUiRepository) {
        this.formUiService = formUiService;
        this.formUiRepository = formUiRepository;
    }

    /**
     * {@code POST  /form-uis} : Create a new formUi.
     *
     * @param formUiDTO the formUiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formUiDTO, or with status {@code 400 (Bad Request)} if the formUi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FormUiDTO> createFormUi(@RequestBody FormUiDTO formUiDTO) throws URISyntaxException {
        log.debug("REST request to save FormUi : {}", formUiDTO);
        if (formUiDTO.getId() != null) {
            throw new BadRequestAlertException("A new formUi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        formUiDTO = formUiService.save(formUiDTO);
        return ResponseEntity.created(new URI("/api/form-uis/" + formUiDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, formUiDTO.getId().toString()))
            .body(formUiDTO);
    }

    /**
     * {@code PUT  /form-uis/:id} : Updates an existing formUi.
     *
     * @param id the id of the formUiDTO to save.
     * @param formUiDTO the formUiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formUiDTO,
     * or with status {@code 400 (Bad Request)} if the formUiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formUiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormUiDTO> updateFormUi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormUiDTO formUiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FormUi : {}, {}", id, formUiDTO);
        if (formUiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formUiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formUiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        formUiDTO = formUiService.update(formUiDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formUiDTO.getId().toString()))
            .body(formUiDTO);
    }

    /**
     * {@code PATCH  /form-uis/:id} : Partial updates given fields of an existing formUi, field will ignore if it is null
     *
     * @param id the id of the formUiDTO to save.
     * @param formUiDTO the formUiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formUiDTO,
     * or with status {@code 400 (Bad Request)} if the formUiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the formUiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the formUiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormUiDTO> partialUpdateFormUi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FormUiDTO formUiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormUi partially : {}, {}", id, formUiDTO);
        if (formUiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formUiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formUiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormUiDTO> result = formUiService.partialUpdate(formUiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, formUiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /form-uis} : get all the formUis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formUis in body.
     */
    @GetMapping("")
    public List<FormUiDTO> getAllFormUis() {
        log.debug("REST request to get all FormUis");
        return formUiService.findAll();
    }

    /**
     * {@code GET  /form-uis/:id} : get the "id" formUi.
     *
     * @param id the id of the formUiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formUiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormUiDTO> getFormUi(@PathVariable("id") Long id) {
        log.debug("REST request to get FormUi : {}", id);
        Optional<FormUiDTO> formUiDTO = formUiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formUiDTO);
    }

    /**
     * {@code DELETE  /form-uis/:id} : delete the "id" formUi.
     *
     * @param id the id of the formUiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormUi(@PathVariable("id") Long id) {
        log.debug("REST request to delete FormUi : {}", id);
        formUiService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
