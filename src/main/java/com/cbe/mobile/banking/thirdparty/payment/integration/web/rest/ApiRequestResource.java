package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ApiRequestRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ApiRequestService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest}.
 */
@RestController
@RequestMapping("/api/api-requests")
public class ApiRequestResource {

    private final Logger log = LoggerFactory.getLogger(ApiRequestResource.class);

    private static final String ENTITY_NAME = "apiRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiRequestService apiRequestService;

    private final ApiRequestRepository apiRequestRepository;

    public ApiRequestResource(ApiRequestService apiRequestService, ApiRequestRepository apiRequestRepository) {
        this.apiRequestService = apiRequestService;
        this.apiRequestRepository = apiRequestRepository;
    }

    /**
     * {@code POST  /api-requests} : Create a new apiRequest.
     *
     * @param apiRequestDTO the apiRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiRequestDTO, or with status {@code 400 (Bad Request)} if the apiRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApiRequestDTO> createApiRequest(@Valid @RequestBody ApiRequestDTO apiRequestDTO) throws URISyntaxException {
        log.debug("REST request to save ApiRequest : {}", apiRequestDTO);
        if (apiRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        apiRequestDTO = apiRequestService.save(apiRequestDTO);
        return ResponseEntity.created(new URI("/api/api-requests/" + apiRequestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, apiRequestDTO.getId().toString()))
            .body(apiRequestDTO);
    }

    /**
     * {@code PUT  /api-requests/:id} : Updates an existing apiRequest.
     *
     * @param id the id of the apiRequestDTO to save.
     * @param apiRequestDTO the apiRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiRequestDTO,
     * or with status {@code 400 (Bad Request)} if the apiRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiRequestDTO> updateApiRequest(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody ApiRequestDTO apiRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApiRequest : {}, {}", id, apiRequestDTO);
        if (apiRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apiRequestDTO = apiRequestService.update(apiRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiRequestDTO.getId().toString()))
            .body(apiRequestDTO);
    }

    /**
     * {@code PATCH  /api-requests/:id} : Partial updates given fields of an existing apiRequest, field will ignore if it is null
     *
     * @param id the id of the apiRequestDTO to save.
     * @param apiRequestDTO the apiRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiRequestDTO,
     * or with status {@code 400 (Bad Request)} if the apiRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apiRequestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApiRequestDTO> partialUpdateApiRequest(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody ApiRequestDTO apiRequestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiRequest partially : {}, {}", id, apiRequestDTO);
        if (apiRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiRequestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiRequestDTO> result = apiRequestService.partialUpdate(apiRequestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiRequestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /api-requests} : get all the apiRequests.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiRequests in body.
     */
    @GetMapping("")
    public List<ApiRequestDTO> getAllApiRequests(@RequestParam(name = "filter", required = false) String filter) {
        if ("paymentdetail-is-null".equals(filter)) {
            log.debug("REST request to get all ApiRequests where paymentDetail is null");
            return apiRequestService.findAllWherePaymentDetailIsNull();
        }
        log.debug("REST request to get all ApiRequests");
        return apiRequestService.findAll();
    }

    /**
     * {@code GET  /api-requests/:id} : get the "id" apiRequest.
     *
     * @param id the id of the apiRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiRequestDTO> getApiRequest(@PathVariable("id") UUID id) {
        log.debug("REST request to get ApiRequest : {}", id);
        Optional<ApiRequestDTO> apiRequestDTO = apiRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiRequestDTO);
    }

    /**
     * {@code DELETE  /api-requests/:id} : delete the "id" apiRequest.
     *
     * @param id the id of the apiRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiRequest(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ApiRequest : {}", id);
        apiRequestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
