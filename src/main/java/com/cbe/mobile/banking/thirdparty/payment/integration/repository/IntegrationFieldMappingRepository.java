package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IntegrationFieldMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegrationFieldMappingRepository extends JpaRepository<IntegrationFieldMapping, UUID> {}
