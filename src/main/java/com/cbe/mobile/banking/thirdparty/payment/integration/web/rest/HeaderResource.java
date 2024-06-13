package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.HeaderRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.HeaderService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header}.
 */
@RestController
@RequestMapping("/api/headers")
public class HeaderResource {

    private final Logger log = LoggerFactory.getLogger(HeaderResource.class);

    private static final String ENTITY_NAME = "header";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeaderService headerService;

    private final HeaderRepository headerRepository;

    public HeaderResource(HeaderService headerService, HeaderRepository headerRepository) {
        this.headerService = headerService;
        this.headerRepository = headerRepository;
    }

    /**
     * {@code POST  /headers} : Create a new header.
     *
     * @param header the header to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new header, or with status {@code 400 (Bad Request)} if the header has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Header> createHeader(@Valid @RequestBody Header header) throws URISyntaxException {
        log.debug("REST request to save Header : {}", header);
        if (header.getId() != null) {
            throw new BadRequestAlertException("A new header cannot already have an ID", ENTITY_NAME, "idexists");
        }
        header = headerService.save(header);
        return ResponseEntity.created(new URI("/api/headers/" + header.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, header.getId().toString()))
            .body(header);
    }

    /**
     * {@code PUT  /headers/:id} : Updates an existing header.
     *
     * @param id the id of the header to save.
     * @param header the header to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated header,
     * or with status {@code 400 (Bad Request)} if the header is not valid,
     * or with status {@code 500 (Internal Server Error)} if the header couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Header> updateHeader(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody Header header
    ) throws URISyntaxException {
        log.debug("REST request to update Header : {}, {}", id, header);
        if (header.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, header.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!headerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        header = headerService.update(header);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, header.getId().toString()))
            .body(header);
    }

    /**
     * {@code PATCH  /headers/:id} : Partial updates given fields of an existing header, field will ignore if it is null
     *
     * @param id the id of the header to save.
     * @param header the header to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated header,
     * or with status {@code 400 (Bad Request)} if the header is not valid,
     * or with status {@code 404 (Not Found)} if the header is not found,
     * or with status {@code 500 (Internal Server Error)} if the header couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Header> partialUpdateHeader(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody Header header
    ) throws URISyntaxException {
        log.debug("REST request to partial update Header partially : {}, {}", id, header);
        if (header.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, header.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!headerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Header> result = headerService.partialUpdate(header);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, header.getId().toString())
        );
    }

    /**
     * {@code GET  /headers} : get all the headers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of headers in body.
     */
    @GetMapping("")
    public List<Header> getAllHeaders() {
        log.debug("REST request to get all Headers");
        return headerService.findAll();
    }

    /**
     * {@code GET  /headers/:id} : get the "id" header.
     *
     * @param id the id of the header to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the header, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Header> getHeader(@PathVariable("id") UUID id) {
        log.debug("REST request to get Header : {}", id);
        Optional<Header> header = headerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(header);
    }

    /**
     * {@code DELETE  /headers/:id} : delete the "id" header.
     *
     * @param id the id of the header to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeader(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Header : {}", id);
        headerService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
