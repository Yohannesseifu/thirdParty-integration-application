package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApiIntegration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiIntegrationRepository extends JpaRepository<ApiIntegration, Long> {}
