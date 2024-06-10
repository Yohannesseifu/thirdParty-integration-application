package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.repository.HeaderRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.HeaderService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.HeaderDTO;
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
     * @param headerDTO the headerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new headerDTO, or with status {@code 400 (Bad Request)} if the header has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HeaderDTO> createHeader(@Valid @RequestBody HeaderDTO headerDTO) throws URISyntaxException {
        log.debug("REST request to save Header : {}", headerDTO);
        if (headerDTO.getId() != null) {
            throw new BadRequestAlertException("A new header cannot already have an ID", ENTITY_NAME, "idexists");
        }
        headerDTO = headerService.save(headerDTO);
        return ResponseEntity.created(new URI("/api/headers/" + headerDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, headerDTO.getId().toString()))
            .body(headerDTO);
    }

    /**
     * {@code PUT  /headers/:id} : Updates an existing header.
     *
     * @param id the id of the headerDTO to save.
     * @param headerDTO the headerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated headerDTO,
     * or with status {@code 400 (Bad Request)} if the headerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the headerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HeaderDTO> updateHeader(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody HeaderDTO headerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Header : {}, {}", id, headerDTO);
        if (headerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, headerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!headerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        headerDTO = headerService.update(headerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, headerDTO.getId().toString()))
            .body(headerDTO);
    }

    /**
     * {@code PATCH  /headers/:id} : Partial updates given fields of an existing header, field will ignore if it is null
     *
     * @param id the id of the headerDTO to save.
     * @param headerDTO the headerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated headerDTO,
     * or with status {@code 400 (Bad Request)} if the headerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the headerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the headerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HeaderDTO> partialUpdateHeader(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody HeaderDTO headerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Header partially : {}, {}", id, headerDTO);
        if (headerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, headerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!headerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HeaderDTO> result = headerService.partialUpdate(headerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, headerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /headers} : get all the headers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of headers in body.
     */
    @GetMapping("")
    public List<HeaderDTO> getAllHeaders() {
        log.debug("REST request to get all Headers");
        return headerService.findAll();
    }

    /**
     * {@code GET  /headers/:id} : get the "id" header.
     *
     * @param id the id of the headerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the headerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HeaderDTO> getHeader(@PathVariable("id") UUID id) {
        log.debug("REST request to get Header : {}", id);
        Optional<HeaderDTO> headerDTO = headerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(headerDTO);
    }

    /**
     * {@code DELETE  /headers/:id} : delete the "id" header.
     *
     * @param id the id of the headerDTO to delete.
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
