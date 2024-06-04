package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ApiIntegrationService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiIntegrationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration}.
 */
@RestController
@RequestMapping("/api/api-integrations")
public class ApiIntegrationResource {

    private final Logger log = LoggerFactory.getLogger(ApiIntegrationResource.class);

    private static final String ENTITY_NAME = "apiIntegration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiIntegrationService apiIntegrationService;

    private final ApiIntegrationRepository apiIntegrationRepository;

    public ApiIntegrationResource(ApiIntegrationService apiIntegrationService, ApiIntegrationRepository apiIntegrationRepository) {
        this.apiIntegrationService = apiIntegrationService;
        this.apiIntegrationRepository = apiIntegrationRepository;
    }

    /**
     * {@code POST  /api-integrations} : Create a new apiIntegration.
     *
     * @param apiIntegrationDTO the apiIntegrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiIntegrationDTO, or with status {@code 400 (Bad Request)} if the apiIntegration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApiIntegrationDTO> createApiIntegration(@Valid @RequestBody ApiIntegrationDTO apiIntegrationDTO)
        throws URISyntaxException {
        log.debug("REST request to save ApiIntegration : {}", apiIntegrationDTO);
        if (apiIntegrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiIntegration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        apiIntegrationDTO = apiIntegrationService.save(apiIntegrationDTO);
        return ResponseEntity.created(new URI("/api/api-integrations/" + apiIntegrationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, apiIntegrationDTO.getId().toString()))
            .body(apiIntegrationDTO);
    }

    /**
     * {@code PUT  /api-integrations/:id} : Updates an existing apiIntegration.
     *
     * @param id the id of the apiIntegrationDTO to save.
     * @param apiIntegrationDTO the apiIntegrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiIntegrationDTO,
     * or with status {@code 400 (Bad Request)} if the apiIntegrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiIntegrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiIntegrationDTO> updateApiIntegration(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApiIntegrationDTO apiIntegrationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApiIntegration : {}, {}", id, apiIntegrationDTO);
        if (apiIntegrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiIntegrationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiIntegrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apiIntegrationDTO = apiIntegrationService.update(apiIntegrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiIntegrationDTO.getId().toString()))
            .body(apiIntegrationDTO);
    }

    /**
     * {@code PATCH  /api-integrations/:id} : Partial updates given fields of an existing apiIntegration, field will ignore if it is null
     *
     * @param id the id of the apiIntegrationDTO to save.
     * @param apiIntegrationDTO the apiIntegrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiIntegrationDTO,
     * or with status {@code 400 (Bad Request)} if the apiIntegrationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apiIntegrationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiIntegrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApiIntegrationDTO> partialUpdateApiIntegration(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApiIntegrationDTO apiIntegrationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiIntegration partially : {}, {}", id, apiIntegrationDTO);
        if (apiIntegrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiIntegrationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiIntegrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiIntegrationDTO> result = apiIntegrationService.partialUpdate(apiIntegrationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiIntegrationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /api-integrations} : get all the apiIntegrations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiIntegrations in body.
     */
    @GetMapping("")
    public List<ApiIntegrationDTO> getAllApiIntegrations() {
        log.debug("REST request to get all ApiIntegrations");
        return apiIntegrationService.findAll();
    }

    /**
     * {@code GET  /api-integrations/:id} : get the "id" apiIntegration.
     *
     * @param id the id of the apiIntegrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiIntegrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiIntegrationDTO> getApiIntegration(@PathVariable("id") Long id) {
        log.debug("REST request to get ApiIntegration : {}", id);
        Optional<ApiIntegrationDTO> apiIntegrationDTO = apiIntegrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiIntegrationDTO);
    }

    /**
     * {@code DELETE  /api-integrations/:id} : delete the "id" apiIntegration.
     *
     * @param id the id of the apiIntegrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiIntegration(@PathVariable("id") Long id) {
        log.debug("REST request to delete ApiIntegration : {}", id);
        apiIntegrationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
