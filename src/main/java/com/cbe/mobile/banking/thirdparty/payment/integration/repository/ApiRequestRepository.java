package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApiRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiRequestRepository extends JpaRepository<ApiRequest, UUID> {}
