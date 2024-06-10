package com.cbe.mobile.banking.thirdparty.payment.integration.web.rest;

import com.cbe.mobile.banking.thirdparty.payment.integration.repository.PaymentDetailRepository;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.PaymentDetailService;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.PaymentDetailDTO;
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
 * REST controller for managing {@link com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail}.
 */
@RestController
@RequestMapping("/api/payment-details")
public class PaymentDetailResource {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailResource.class);

    private static final String ENTITY_NAME = "paymentDetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentDetailService paymentDetailService;

    private final PaymentDetailRepository paymentDetailRepository;

    public PaymentDetailResource(PaymentDetailService paymentDetailService, PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailService = paymentDetailService;
        this.paymentDetailRepository = paymentDetailRepository;
    }

    /**
     * {@code POST  /payment-details} : Create a new paymentDetail.
     *
     * @param paymentDetailDTO the paymentDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentDetailDTO, or with status {@code 400 (Bad Request)} if the paymentDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentDetailDTO> createPaymentDetail(@Valid @RequestBody PaymentDetailDTO paymentDetailDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentDetail : {}", paymentDetailDTO);
        if (paymentDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentDetailDTO = paymentDetailService.save(paymentDetailDTO);
        return ResponseEntity.created(new URI("/api/payment-details/" + paymentDetailDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, paymentDetailDTO.getId().toString()))
            .body(paymentDetailDTO);
    }

    /**
     * {@code PUT  /payment-details/:id} : Updates an existing paymentDetail.
     *
     * @param id the id of the paymentDetailDTO to save.
     * @param paymentDetailDTO the paymentDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDetailDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDetailDTO> updatePaymentDetail(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PaymentDetailDTO paymentDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentDetail : {}, {}", id, paymentDetailDTO);
        if (paymentDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentDetailDTO = paymentDetailService.update(paymentDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDetailDTO.getId().toString()))
            .body(paymentDetailDTO);
    }

    /**
     * {@code PATCH  /payment-details/:id} : Partial updates given fields of an existing paymentDetail, field will ignore if it is null
     *
     * @param id the id of the paymentDetailDTO to save.
     * @param paymentDetailDTO the paymentDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDetailDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDetailDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentDetailDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentDetailDTO> partialUpdatePaymentDetail(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PaymentDetailDTO paymentDetailDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentDetail partially : {}, {}", id, paymentDetailDTO);
        if (paymentDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentDetailDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentDetailRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentDetailDTO> result = paymentDetailService.partialUpdate(paymentDetailDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDetailDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-details} : get all the paymentDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentDetails in body.
     */
    @GetMapping("")
    public List<PaymentDetailDTO> getAllPaymentDetails() {
        log.debug("REST request to get all PaymentDetails");
        return paymentDetailService.findAll();
    }

    /**
     * {@code GET  /payment-details/:id} : get the "id" paymentDetail.
     *
     * @param id the id of the paymentDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailDTO> getPaymentDetail(@PathVariable("id") UUID id) {
        log.debug("REST request to get PaymentDetail : {}", id);
        Optional<PaymentDetailDTO> paymentDetailDTO = paymentDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentDetailDTO);
    }

    /**
     * {@code DELETE  /payment-details/:id} : delete the "id" paymentDetail.
     *
     * @param id the id of the paymentDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentDetail(@PathVariable("id") UUID id) {
        log.debug("REST request to delete PaymentDetail : {}", id);
        paymentDetailService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
