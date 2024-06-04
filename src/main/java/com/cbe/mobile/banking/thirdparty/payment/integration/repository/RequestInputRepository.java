package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RequestInput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestInputRepository extends JpaRepository<RequestInput, Long> {}
