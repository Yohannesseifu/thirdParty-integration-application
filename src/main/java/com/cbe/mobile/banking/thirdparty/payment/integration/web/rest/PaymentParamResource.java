package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentParamRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentParamService;
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
     * @param paymentParam the paymentParam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentParam, or with status {@code 400 (Bad Request)} if the paymentParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentParam> createPaymentParam(@Valid @RequestBody PaymentParam paymentParam) throws URISyntaxException {
        log.debug("REST request to save PaymentParam : {}", paymentParam);
        if (paymentParam.getId() != null) {
            throw new BadRequestAlertException("A new paymentParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentParam = paymentParamService.save(paymentParam);
        return ResponseEntity.created(new URI("/api/payment-params/" + paymentParam.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, paymentParam.getId().toString()))
            .body(paymentParam);
    }

    /**
     * {@code PUT  /payment-params/:id} : Updates an existing paymentParam.
     *
     * @param id the id of the paymentParam to save.
     * @param paymentParam the paymentParam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentParam,
     * or with status {@code 400 (Bad Request)} if the paymentParam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentParam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentParam> updatePaymentParam(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PaymentParam paymentParam
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentParam : {}, {}", id, paymentParam);
        if (paymentParam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentParam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentParamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentParam = paymentParamService.update(paymentParam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentParam.getId().toString()))
            .body(paymentParam);
    }

    /**
     * {@code PATCH  /payment-params/:id} : Partial updates given fields of an existing paymentParam, field will ignore if it is null
     *
     * @param id the id of the paymentParam to save.
     * @param paymentParam the paymentParam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentParam,
     * or with status {@code 400 (Bad Request)} if the paymentParam is not valid,
     * or with status {@code 404 (Not Found)} if the paymentParam is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentParam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentParam> partialUpdatePaymentParam(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PaymentParam paymentParam
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentParam partially : {}, {}", id, paymentParam);
        if (paymentParam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentParam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentParamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentParam> result = paymentParamService.partialUpdate(paymentParam);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentParam.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-params} : get all the paymentParams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentParams in body.
     */
    @GetMapping("")
    public List<PaymentParam> getAllPaymentParams() {
        log.debug("REST request to get all PaymentParams");
        return paymentParamService.findAll();
    }

    /**
     * {@code GET  /payment-params/:id} : get the "id" paymentParam.
     *
     * @param id the id of the paymentParam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentParam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentParam> getPaymentParam(@PathVariable("id") UUID id) {
        log.debug("REST request to get PaymentParam : {}", id);
        Optional<PaymentParam> paymentParam = paymentParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentParam);
    }

    /**
     * {@code DELETE  /payment-params/:id} : delete the "id" paymentParam.
     *
     * @param id the id of the paymentParam to delete.
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
