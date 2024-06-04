package com.cbe.mobile.banking.thirdparty.payment.integration.service.mapper;

import com.cbe.mobile.banking.thirdparty.payment.integration.domain.FieldUIMetaData;
import com.cbe.mobile.banking.thirdparty.payment.integration.service.dto.FieldUIMetaDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldUIMetaData} and its DTO {@link FieldUIMetaDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldUIMetaDataMapper extends EntityMapper<FieldUIMetaDataDTO, FieldUIMetaData> {}
