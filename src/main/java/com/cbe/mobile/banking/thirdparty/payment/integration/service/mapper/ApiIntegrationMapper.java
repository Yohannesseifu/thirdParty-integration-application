package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiIntegration;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiIntegrationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiIntegration} and its DTO {@link ApiIntegrationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApiIntegrationMapper extends EntityMapper<ApiIntegrationDTO, ApiIntegration> {}
