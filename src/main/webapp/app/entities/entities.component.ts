import { defineComponent, provide } from 'vue';

import MenuService from './menu/menu.service';
import ThirdPartyIntegrationService from './third-party-integration/third-party-integration.service';
import FieldService from './field/field.service';
import FieldUIMetaDataService from './field-ui-meta-data/field-ui-meta-data.service';
import FormUiService from './form-ui/form-ui.service';
import ApiIntegrationService from './api-integration/api-integration.service';
import OperationService from './operation/operation.service';
import RequestInputService from './request-input/request-input.service';
import ResponseOutputService from './response-output/response-output.service';
import PaymentDetailService from './payment-detail/payment-detail.service';
import PaymentParamService from './payment-param/payment-param.service';
import HeaderService from './header/header.service';
import ApiRequestService from './api-request/api-request.service';
import IntegrationOperationService from './integration-operation/integration-operation.service';
import IntegrationFieldMappingService from './integration-field-mapping/integration-field-mapping.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('menuService', () => new MenuService());
    provide('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());
    provide('fieldService', () => new FieldService());
    provide('fieldUIMetaDataService', () => new FieldUIMetaDataService());
    provide('formUiService', () => new FormUiService());
    provide('apiIntegrationService', () => new ApiIntegrationService());
    provide('operationService', () => new OperationService());
    provide('requestInputService', () => new RequestInputService());
    provide('responseOutputService', () => new ResponseOutputService());
    provide('paymentDetailService', () => new PaymentDetailService());
    provide('paymentParamService', () => new PaymentParamService());
    provide('headerService', () => new HeaderService());
    provide('apiRequestService', () => new ApiRequestService());
    provide('integrationOperationService', () => new IntegrationOperationService());
    provide('integrationFieldMappingService', () => new IntegrationFieldMappingService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
