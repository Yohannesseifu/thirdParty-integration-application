package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.ApiRequest;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Header;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.ApiRequestDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.HeaderDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Header} and its DTO {@link HeaderDTO}.
 */
@Mapper(componentModel = "spring")
public interface HeaderMapper extends EntityMapper<HeaderDTO, Header> {
    @Mapping(target = "apiRequest", source = "apiRequest", qualifiedByName = "apiRequestId")
    HeaderDTO toDto(Header s);

    @Named("apiRequestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApiRequestDTO toDtoApiRequestId(ApiRequest apiRequest);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
