package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.RequestInputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.RequestInputService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput}.
 */
@RestController
@RequestMapping("/api/request-inputs")
public class RequestInputResource {

    private final Logger log = LoggerFactory.getLogger(RequestInputResource.class);

    private static final String ENTITY_NAME = "requestInput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestInputService requestInputService;

    private final RequestInputRepository requestInputRepository;

    public RequestInputResource(RequestInputService requestInputService, RequestInputRepository requestInputRepository) {
        this.requestInputService = requestInputService;
        this.requestInputRepository = requestInputRepository;
    }

    /**
     * {@code POST  /request-inputs} : Create a new requestInput.
     *
     * @param requestInput the requestInput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestInput, or with status {@code 400 (Bad Request)} if the requestInput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RequestInput> createRequestInput(@Valid @RequestBody RequestInput requestInput) throws URISyntaxException {
        log.debug("REST request to save RequestInput : {}", requestInput);
        if (requestInput.getId() != null) {
            throw new BadRequestAlertException("A new requestInput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        requestInput = requestInputService.save(requestInput);
        return ResponseEntity.created(new URI("/api/request-inputs/" + requestInput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, requestInput.getId().toString()))
            .body(requestInput);
    }

    /**
     * {@code PUT  /request-inputs/:id} : Updates an existing requestInput.
     *
     * @param id the id of the requestInput to save.
     * @param requestInput the requestInput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestInput,
     * or with status {@code 400 (Bad Request)} if the requestInput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestInput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RequestInput> updateRequestInput(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody RequestInput requestInput
    ) throws URISyntaxException {
        log.debug("REST request to update RequestInput : {}, {}", id, requestInput);
        if (requestInput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestInput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestInputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        requestInput = requestInputService.update(requestInput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestInput.getId().toString()))
            .body(requestInput);
    }

    /**
     * {@code PATCH  /request-inputs/:id} : Partial updates given fields of an existing requestInput, field will ignore if it is null
     *
     * @param id the id of the requestInput to save.
     * @param requestInput the requestInput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestInput,
     * or with status {@code 400 (Bad Request)} if the requestInput is not valid,
     * or with status {@code 404 (Not Found)} if the requestInput is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestInput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestInput> partialUpdateRequestInput(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody RequestInput requestInput
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestInput partially : {}, {}", id, requestInput);
        if (requestInput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestInput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestInputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestInput> result = requestInputService.partialUpdate(requestInput);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestInput.getId().toString())
        );
    }

    /**
     * {@code GET  /request-inputs} : get all the requestInputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestInputs in body.
     */
    @GetMapping("")
    public List<RequestInput> getAllRequestInputs() {
        log.debug("REST request to get all RequestInputs");
        return requestInputService.findAll();
    }

    /**
     * {@code GET  /request-inputs/:id} : get the "id" requestInput.
     *
     * @param id the id of the requestInput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestInput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RequestInput> getRequestInput(@PathVariable("id") UUID id) {
        log.debug("REST request to get RequestInput : {}", id);
        Optional<RequestInput> requestInput = requestInputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestInput);
    }

    /**
     * {@code DELETE  /request-inputs/:id} : delete the "id" requestInput.
     *
     * @param id the id of the requestInput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestInput(@PathVariable("id") UUID id) {
        log.debug("REST request to delete RequestInput : {}", id);
        requestInputService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
