package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.PaymentParam;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentParamRepository extends JpaRepository<PaymentParam, UUID> {}
