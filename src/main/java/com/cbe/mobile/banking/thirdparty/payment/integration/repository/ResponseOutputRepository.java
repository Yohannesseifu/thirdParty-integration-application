package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ResponseOutput;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResponseOutput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponseOutputRepository extends JpaRepository<ResponseOutput, UUID> {}
