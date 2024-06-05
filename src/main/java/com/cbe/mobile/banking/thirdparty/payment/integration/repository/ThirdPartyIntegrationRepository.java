package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ThirdPartyIntegration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ThirdPartyIntegration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThirdPartyIntegrationRepository extends JpaRepository<ThirdPartyIntegration, Long> {}
