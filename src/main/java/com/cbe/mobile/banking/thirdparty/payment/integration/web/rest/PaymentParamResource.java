package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentParamRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentParamService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentParamDTO;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam}.
 */
@RestController
@RequestMapping("/api/payment-params")
public class PaymentParamResource {

    private final Logger log = LoggerFactory.getLogger(PaymentParamResource.class);

    private static final String ENTITY_NAME = "paymentParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentParamService paymentParamService;

    private final PaymentParamRepository paymentParamRepository;

    public PaymentParamResource(PaymentParamService paymentParamService, PaymentParamRepository paymentParamRepository) {
        this.paymentParamService = paymentParamService;
        this.paymentParamRepository = paymentParamRepository;
    }

    /**
     * {@code POST  /payment-params} : Create a new paymentParam.
     *
     * @param paymentParamDTO the paymentParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentParamDTO, or with status {@code 400 (Bad Request)} if the paymentParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentParamDTO> createPaymentParam(@Valid @RequestBody PaymentParamDTO paymentParamDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentParam : {}", paymentParamDTO);
        if (paymentParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentParamDTO = paymentParamService.save(paymentParamDTO);
        return ResponseEntity.created(new URI("/api/payment-params/" + paymentParamDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, paymentParamDTO.getId().toString()))
            .body(paymentParamDTO);
    }

    /**
     * {@code PUT  /payment-params/:id} : Updates an existing paymentParam.
     *
     * @param id the id of the paymentParamDTO to save.
     * @param paymentParamDTO the paymentParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentParamDTO,
     * or with status {@code 400 (Bad Request)} if the paymentParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentParamDTO> updatePaymentParam(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PaymentParamDTO paymentParamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentParam : {}, {}", id, paymentParamDTO);
        if (paymentParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentParamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentParamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentParamDTO = paymentParamService.update(paymentParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentParamDTO.getId().toString()))
            .body(paymentParamDTO);
    }

    /**
     * {@code PATCH  /payment-params/:id} : Partial updates given fields of an existing paymentParam, field will ignore if it is null
     *
     * @param id the id of the paymentParamDTO to save.
     * @param paymentParamDTO the paymentParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentParamDTO,
     * or with status {@code 400 (Bad Request)} if the paymentParamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentParamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentParamDTO> partialUpdatePaymentParam(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PaymentParamDTO paymentParamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentParam partially : {}, {}", id, paymentParamDTO);
        if (paymentParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentParamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentParamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentParamDTO> result = paymentParamService.partialUpdate(paymentParamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentParamDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-params} : get all the paymentParams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentParams in body.
     */
    @GetMapping("")
    public List<PaymentParamDTO> getAllPaymentParams() {
        log.debug("REST request to get all PaymentParams");
        return paymentParamService.findAll();
    }

    /**
     * {@code GET  /payment-params/:id} : get the "id" paymentParam.
     *
     * @param id the id of the paymentParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentParamDTO> getPaymentParam(@PathVariable("id") UUID id) {
        log.debug("REST request to get PaymentParam : {}", id);
        Optional<PaymentParamDTO> paymentParamDTO = paymentParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentParamDTO);
    }

    /**
     * {@code DELETE  /payment-params/:id} : delete the "id" paymentParam.
     *
     * @param id the id of the paymentParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentParam(@PathVariable("id") UUID id) {
        log.debug("REST request to delete PaymentParam : {}", id);
        paymentParamService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
