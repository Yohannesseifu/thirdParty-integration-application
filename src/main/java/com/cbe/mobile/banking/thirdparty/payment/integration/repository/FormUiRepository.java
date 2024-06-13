package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormUi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormUiRepository extends JpaRepository<FormUi, UUID> {}
