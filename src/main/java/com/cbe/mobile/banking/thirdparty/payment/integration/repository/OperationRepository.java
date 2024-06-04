package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Operation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Operation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {}
