package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ResponseOutputRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ResponseOutputService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput}.
 */
@RestController
@RequestMapping("/api/response-outputs")
public class ResponseOutputResource {

    private final Logger log = LoggerFactory.getLogger(ResponseOutputResource.class);

    private static final String ENTITY_NAME = "responseOutput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponseOutputService responseOutputService;

    private final ResponseOutputRepository responseOutputRepository;

    public ResponseOutputResource(ResponseOutputService responseOutputService, ResponseOutputRepository responseOutputRepository) {
        this.responseOutputService = responseOutputService;
        this.responseOutputRepository = responseOutputRepository;
    }

    /**
     * {@code POST  /response-outputs} : Create a new responseOutput.
     *
     * @param responseOutput the responseOutput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responseOutput, or with status {@code 400 (Bad Request)} if the responseOutput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ResponseOutput> createResponseOutput(@Valid @RequestBody ResponseOutput responseOutput)
        throws URISyntaxException {
        log.debug("REST request to save ResponseOutput : {}", responseOutput);
        if (responseOutput.getId() != null) {
            throw new BadRequestAlertException("A new responseOutput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        responseOutput = responseOutputService.save(responseOutput);
        return ResponseEntity.created(new URI("/api/response-outputs/" + responseOutput.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, responseOutput.getId().toString()))
            .body(responseOutput);
    }

    /**
     * {@code PUT  /response-outputs/:id} : Updates an existing responseOutput.
     *
     * @param id the id of the responseOutput to save.
     * @param responseOutput the responseOutput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responseOutput,
     * or with status {@code 400 (Bad Request)} if the responseOutput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responseOutput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseOutput> updateResponseOutput(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ResponseOutput responseOutput
    ) throws URISyntaxException {
        log.debug("REST request to update ResponseOutput : {}, {}", id, responseOutput);
        if (responseOutput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responseOutput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responseOutputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        responseOutput = responseOutputService.update(responseOutput);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responseOutput.getId().toString()))
            .body(responseOutput);
    }

    /**
     * {@code PATCH  /response-outputs/:id} : Partial updates given fields of an existing responseOutput, field will ignore if it is null
     *
     * @param id the id of the responseOutput to save.
     * @param responseOutput the responseOutput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responseOutput,
     * or with status {@code 400 (Bad Request)} if the responseOutput is not valid,
     * or with status {@code 404 (Not Found)} if the responseOutput is not found,
     * or with status {@code 500 (Internal Server Error)} if the responseOutput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponseOutput> partialUpdateResponseOutput(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ResponseOutput responseOutput
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponseOutput partially : {}, {}", id, responseOutput);
        if (responseOutput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responseOutput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responseOutputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponseOutput> result = responseOutputService.partialUpdate(responseOutput);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, responseOutput.getId().toString())
        );
    }

    /**
     * {@code GET  /response-outputs} : get all the responseOutputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responseOutputs in body.
     */
    @GetMapping("")
    public List<ResponseOutput> getAllResponseOutputs() {
        log.debug("REST request to get all ResponseOutputs");
        return responseOutputService.findAll();
    }

    /**
     * {@code GET  /response-outputs/:id} : get the "id" responseOutput.
     *
     * @param id the id of the responseOutput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responseOutput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseOutput> getResponseOutput(@PathVariable("id") UUID id) {
        log.debug("REST request to get ResponseOutput : {}", id);
        Optional<ResponseOutput> responseOutput = responseOutputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responseOutput);
    }

    /**
     * {@code DELETE  /response-outputs/:id} : delete the "id" responseOutput.
     *
     * @param id the id of the responseOutput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponseOutput(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ResponseOutput : {}", id);
        responseOutputService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
