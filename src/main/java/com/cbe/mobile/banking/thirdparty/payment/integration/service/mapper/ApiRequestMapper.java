package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiRequest} and its DTO {@link ApiRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApiRequestMapper extends EntityMapper<ApiRequestDTO, ApiRequest> {}
