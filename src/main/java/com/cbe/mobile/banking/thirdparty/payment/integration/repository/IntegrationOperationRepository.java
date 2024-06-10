package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntegrationOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegrationOperationRepository extends JpaRepository<IntegrationOperation, UUID> {}
