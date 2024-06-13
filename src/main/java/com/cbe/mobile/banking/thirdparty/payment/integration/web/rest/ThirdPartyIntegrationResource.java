package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.ThirdPartyIntegrationRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.ThirdPartyIntegrationService;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration}.
 */
@RestController
@RequestMapping("/api/third-party-integrations")
public class ThirdPartyIntegrationResource {

    private final Logger log = LoggerFactory.getLogger(ThirdPartyIntegrationResource.class);

    private static final String ENTITY_NAME = "thirdPartyIntegration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThirdPartyIntegrationService thirdPartyIntegrationService;

    private final ThirdPartyIntegrationRepository thirdPartyIntegrationRepository;

    public ThirdPartyIntegrationResource(
        ThirdPartyIntegrationService thirdPartyIntegrationService,
        ThirdPartyIntegrationRepository thirdPartyIntegrationRepository
    ) {
        this.thirdPartyIntegrationService = thirdPartyIntegrationService;
        this.thirdPartyIntegrationRepository = thirdPartyIntegrationRepository;
    }

    /**
     * {@code POST  /third-party-integrations} : Create a new thirdPartyIntegration.
     *
     * @param thirdPartyIntegration the thirdPartyIntegration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thirdPartyIntegration, or with status {@code 400 (Bad Request)} if the thirdPartyIntegration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ThirdPartyIntegration> createThirdPartyIntegration(@RequestBody ThirdPartyIntegration thirdPartyIntegration)
        throws URISyntaxException {
        log.debug("REST request to save ThirdPartyIntegration : {}", thirdPartyIntegration);
        if (thirdPartyIntegration.getId() != null) {
            throw new BadRequestAlertException("A new thirdPartyIntegration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        thirdPartyIntegration = thirdPartyIntegrationService.save(thirdPartyIntegration);
        return ResponseEntity.created(new URI("/api/third-party-integrations/" + thirdPartyIntegration.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, thirdPartyIntegration.getId().toString()))
            .body(thirdPartyIntegration);
    }

    /**
     * {@code PUT  /third-party-integrations/:id} : Updates an existing thirdPartyIntegration.
     *
     * @param id the id of the thirdPartyIntegration to save.
     * @param thirdPartyIntegration the thirdPartyIntegration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thirdPartyIntegration,
     * or with status {@code 400 (Bad Request)} if the thirdPartyIntegration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thirdPartyIntegration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ThirdPartyIntegration> updateThirdPartyIntegration(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ThirdPartyIntegration thirdPartyIntegration
    ) throws URISyntaxException {
        log.debug("REST request to update ThirdPartyIntegration : {}, {}", id, thirdPartyIntegration);
        if (thirdPartyIntegration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thirdPartyIntegration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thirdPartyIntegrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        thirdPartyIntegration = thirdPartyIntegrationService.update(thirdPartyIntegration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thirdPartyIntegration.getId().toString()))
            .body(thirdPartyIntegration);
    }

    /**
     * {@code PATCH  /third-party-integrations/:id} : Partial updates given fields of an existing thirdPartyIntegration, field will ignore if it is null
     *
     * @param id the id of the thirdPartyIntegration to save.
     * @param thirdPartyIntegration the thirdPartyIntegration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thirdPartyIntegration,
     * or with status {@code 400 (Bad Request)} if the thirdPartyIntegration is not valid,
     * or with status {@code 404 (Not Found)} if the thirdPartyIntegration is not found,
     * or with status {@code 500 (Internal Server Error)} if the thirdPartyIntegration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThirdPartyIntegration> partialUpdateThirdPartyIntegration(
        @PathVariable(value = "id", required = false) final UUID id,
        @RequestBody ThirdPartyIntegration thirdPartyIntegration
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThirdPartyIntegration partially : {}, {}", id, thirdPartyIntegration);
        if (thirdPartyIntegration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thirdPartyIntegration.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thirdPartyIntegrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThirdPartyIntegration> result = thirdPartyIntegrationService.partialUpdate(thirdPartyIntegration);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thirdPartyIntegration.getId().toString())
        );
    }

    /**
     * {@code GET  /third-party-integrations} : get all the thirdPartyIntegrations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thirdPartyIntegrations in body.
     */
    @GetMapping("")
    public List<ThirdPartyIntegration> getAllThirdPartyIntegrations() {
        log.debug("REST request to get all ThirdPartyIntegrations");
        return thirdPartyIntegrationService.findAll();
    }

    /**
     * {@code GET  /third-party-integrations/:id} : get the "id" thirdPartyIntegration.
     *
     * @param id the id of the thirdPartyIntegration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thirdPartyIntegration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyIntegration> getThirdPartyIntegration(@PathVariable("id") UUID id) {
        log.debug("REST request to get ThirdPartyIntegration : {}", id);
        Optional<ThirdPartyIntegration> thirdPartyIntegration = thirdPartyIntegrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thirdPartyIntegration);
    }

    /**
     * {@code DELETE  /third-party-integrations/:id} : delete the "id" thirdPartyIntegration.
     *
     * @param id the id of the thirdPartyIntegration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThirdPartyIntegration(@PathVariable("id") UUID id) {
        log.debug("REST request to delete ThirdPartyIntegration : {}", id);
        thirdPartyIntegrationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
