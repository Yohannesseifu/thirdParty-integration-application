package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentDetail;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, UUID> {}
