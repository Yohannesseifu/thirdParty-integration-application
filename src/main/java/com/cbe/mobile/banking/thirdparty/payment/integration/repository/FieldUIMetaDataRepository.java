package com.cbe.mobile.banking.thirdparty.payment.integration.repository;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldUIMetaData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldUIMetaDataRepository extends JpaRepository<FieldUIMetaData, UUID> {}
