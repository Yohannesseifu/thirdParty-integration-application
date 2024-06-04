package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.Field;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FormUi;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldUIMetaDataDTO;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FormUiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Field} and its DTO {@link FieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldMapper extends EntityMapper<FieldDTO, Field> {
    @Mapping(target = "fieldUIMetaData", source = "fieldUIMetaData", qualifiedByName = "fieldUIMetaDataId")
    @Mapping(target = "formUi", source = "formUi", qualifiedByName = "formUiId")
    FieldDTO toDto(Field s);

    @Named("fieldUIMetaDataId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldUIMetaDataDTO toDtoFieldUIMetaDataId(FieldUIMetaData fieldUIMetaData);

    @Named("formUiId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FormUiDTO toDtoFormUiId(FormUi formUi);
}
