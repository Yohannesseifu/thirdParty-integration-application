package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationFieldMapping;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.IntegrationOperation;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.RequestInput;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationFieldMappingDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.IntegrationOperationDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.RequestInputDTO;
import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegrationFieldMapping} and its DTO {@link IntegrationFieldMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegrationFieldMappingMapper extends EntityMapper<IntegrationFieldMappingDTO, IntegrationFieldMapping> {
    @Mapping(target = "integrationOperation", source = "integrationOperation", qualifiedByName = "integrationOperationId")
    @Mapping(target = "field", source = "field", qualifiedByName = "fieldId")
    @Mapping(target = "requestInput", source = "requestInput", qualifiedByName = "requestInputId")
    IntegrationFieldMappingDTO toDto(IntegrationFieldMapping s);

    @Named("integrationOperationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IntegrationOperationDTO toDtoIntegrationOperationId(IntegrationOperation integrationOperation);

    @Named("fieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldDTO toDtoFieldId(Field field);

    @Named("requestInputId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequestInputDTO toDtoRequestInputId(RequestInput requestInput);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
