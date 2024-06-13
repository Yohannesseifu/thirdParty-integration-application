import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Menu = () => import('@/entities/menu/menu.vue');
const MenuUpdate = () => import('@/entities/menu/menu-update.vue');
const MenuDetails = () => import('@/entities/menu/menu-details.vue');

const ThirdPartyIntegration = () => import('@/entities/third-party-integration/third-party-integration.vue');
const ThirdPartyIntegrationUpdate = () => import('@/entities/third-party-integration/third-party-integration-update.vue');
const ThirdPartyIntegrationDetails = () => import('@/entities/third-party-integration/third-party-integration-details.vue');

const Field = () => import('@/entities/field/field.vue');
const FieldUpdate = () => import('@/entities/field/field-update.vue');
const FieldDetails = () => import('@/entities/field/field-details.vue');

const FieldUIMetaData = () => import('@/entities/field-ui-meta-data/field-ui-meta-data.vue');
const FieldUIMetaDataUpdate = () => import('@/entities/field-ui-meta-data/field-ui-meta-data-update.vue');
const FieldUIMetaDataDetails = () => import('@/entities/field-ui-meta-data/field-ui-meta-data-details.vue');

const FormUi = () => import('@/entities/form-ui/form-ui.vue');
const FormUiUpdate = () => import('@/entities/form-ui/form-ui-update.vue');
const FormUiDetails = () => import('@/entities/form-ui/form-ui-details.vue');

const ApiIntegration = () => import('@/entities/api-integration/api-integration.vue');
const ApiIntegrationUpdate = () => import('@/entities/api-integration/api-integration-update.vue');
const ApiIntegrationDetails = () => import('@/entities/api-integration/api-integration-details.vue');

const Operation = () => import('@/entities/operation/operation.vue');
const OperationUpdate = () => import('@/entities/operation/operation-update.vue');
const OperationDetails = () => import('@/entities/operation/operation-details.vue');

const RequestInput = () => import('@/entities/request-input/request-input.vue');
const RequestInputUpdate = () => import('@/entities/request-input/request-input-update.vue');
const RequestInputDetails = () => import('@/entities/request-input/request-input-details.vue');

const ResponseOutput = () => import('@/entities/response-output/response-output.vue');
const ResponseOutputUpdate = () => import('@/entities/response-output/response-output-update.vue');
const ResponseOutputDetails = () => import('@/entities/response-output/response-output-details.vue');

const PaymentDetail = () => import('@/entities/payment-detail/payment-detail.vue');
const PaymentDetailUpdate = () => import('@/entities/payment-detail/payment-detail-update.vue');
const PaymentDetailDetails = () => import('@/entities/payment-detail/payment-detail-details.vue');

const PaymentParam = () => import('@/entities/payment-param/payment-param.vue');
const PaymentParamUpdate = () => import('@/entities/payment-param/payment-param-update.vue');
const PaymentParamDetails = () => import('@/entities/payment-param/payment-param-details.vue');

const Header = () => import('@/entities/header/header.vue');
const HeaderUpdate = () => import('@/entities/header/header-update.vue');
const HeaderDetails = () => import('@/entities/header/header-details.vue');

const ApiRequest = () => import('@/entities/api-request/api-request.vue');
const ApiRequestUpdate = () => import('@/entities/api-request/api-request-update.vue');
const ApiRequestDetails = () => import('@/entities/api-request/api-request-details.vue');

const IntegrationOperation = () => import('@/entities/integration-operation/integration-operation.vue');
const IntegrationOperationUpdate = () => import('@/entities/integration-operation/integration-operation-update.vue');
const IntegrationOperationDetails = () => import('@/entities/integration-operation/integration-operation-details.vue');

const IntegrationFieldMapping = () => import('@/entities/integration-field-mapping/integration-field-mapping.vue');
const IntegrationFieldMappingUpdate = () => import('@/entities/integration-field-mapping/integration-field-mapping-update.vue');
const IntegrationFieldMappingDetails = () => import('@/entities/integration-field-mapping/integration-field-mapping-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'menu',
      name: 'Menu',
      component: Menu,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'menu/new',
      name: 'MenuCreate',
      component: MenuUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'menu/:menuId/edit',
      name: 'MenuEdit',
      component: MenuUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'menu/:menuId/view',
      name: 'MenuView',
      component: MenuDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'third-party-integration',
      name: 'ThirdPartyIntegration',
      component: ThirdPartyIntegration,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'third-party-integration/new',
      name: 'ThirdPartyIntegrationCreate',
      component: ThirdPartyIntegrationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'third-party-integration/:thirdPartyIntegrationId/edit',
      name: 'ThirdPartyIntegrationEdit',
      component: ThirdPartyIntegrationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'third-party-integration/:thirdPartyIntegrationId/view',
      name: 'ThirdPartyIntegrationView',
      component: ThirdPartyIntegrationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field',
      name: 'Field',
      component: Field,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field/new',
      name: 'FieldCreate',
      component: FieldUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field/:fieldId/edit',
      name: 'FieldEdit',
      component: FieldUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field/:fieldId/view',
      name: 'FieldView',
      component: FieldDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field-ui-meta-data',
      name: 'FieldUIMetaData',
      component: FieldUIMetaData,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field-ui-meta-data/new',
      name: 'FieldUIMetaDataCreate',
      component: FieldUIMetaDataUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field-ui-meta-data/:fieldUIMetaDataId/edit',
      name: 'FieldUIMetaDataEdit',
      component: FieldUIMetaDataUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'field-ui-meta-data/:fieldUIMetaDataId/view',
      name: 'FieldUIMetaDataView',
      component: FieldUIMetaDataDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'form-ui',
      name: 'FormUi',
      component: FormUi,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'form-ui/new',
      name: 'FormUiCreate',
      component: FormUiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'form-ui/:formUiId/edit',
      name: 'FormUiEdit',
      component: FormUiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'form-ui/:formUiId/view',
      name: 'FormUiView',
      component: FormUiDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-integration',
      name: 'ApiIntegration',
      component: ApiIntegration,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-integration/new',
      name: 'ApiIntegrationCreate',
      component: ApiIntegrationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-integration/:apiIntegrationId/edit',
      name: 'ApiIntegrationEdit',
      component: ApiIntegrationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-integration/:apiIntegrationId/view',
      name: 'ApiIntegrationView',
      component: ApiIntegrationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operation',
      name: 'Operation',
      component: Operation,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operation/new',
      name: 'OperationCreate',
      component: OperationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operation/:operationId/edit',
      name: 'OperationEdit',
      component: OperationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'operation/:operationId/view',
      name: 'OperationView',
      component: OperationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'request-input',
      name: 'RequestInput',
      component: RequestInput,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'request-input/new',
      name: 'RequestInputCreate',
      component: RequestInputUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'request-input/:requestInputId/edit',
      name: 'RequestInputEdit',
      component: RequestInputUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'request-input/:requestInputId/view',
      name: 'RequestInputView',
      component: RequestInputDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'response-output',
      name: 'ResponseOutput',
      component: ResponseOutput,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'response-output/new',
      name: 'ResponseOutputCreate',
      component: ResponseOutputUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'response-output/:responseOutputId/edit',
      name: 'ResponseOutputEdit',
      component: ResponseOutputUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'response-output/:responseOutputId/view',
      name: 'ResponseOutputView',
      component: ResponseOutputDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-detail',
      name: 'PaymentDetail',
      component: PaymentDetail,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-detail/new',
      name: 'PaymentDetailCreate',
      component: PaymentDetailUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-detail/:paymentDetailId/edit',
      name: 'PaymentDetailEdit',
      component: PaymentDetailUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-detail/:paymentDetailId/view',
      name: 'PaymentDetailView',
      component: PaymentDetailDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-param',
      name: 'PaymentParam',
      component: PaymentParam,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-param/new',
      name: 'PaymentParamCreate',
      component: PaymentParamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-param/:paymentParamId/edit',
      name: 'PaymentParamEdit',
      component: PaymentParamUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payment-param/:paymentParamId/view',
      name: 'PaymentParamView',
      component: PaymentParamDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'header',
      name: 'Header',
      component: Header,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'header/new',
      name: 'HeaderCreate',
      component: HeaderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'header/:headerId/edit',
      name: 'HeaderEdit',
      component: HeaderUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'header/:headerId/view',
      name: 'HeaderView',
      component: HeaderDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-request',
      name: 'ApiRequest',
      component: ApiRequest,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-request/new',
      name: 'ApiRequestCreate',
      component: ApiRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-request/:apiRequestId/edit',
      name: 'ApiRequestEdit',
      component: ApiRequestUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'api-request/:apiRequestId/view',
      name: 'ApiRequestView',
      component: ApiRequestDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-operation',
      name: 'IntegrationOperation',
      component: IntegrationOperation,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-operation/new',
      name: 'IntegrationOperationCreate',
      component: IntegrationOperationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-operation/:integrationOperationId/edit',
      name: 'IntegrationOperationEdit',
      component: IntegrationOperationUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-operation/:integrationOperationId/view',
      name: 'IntegrationOperationView',
      component: IntegrationOperationDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-field-mapping',
      name: 'IntegrationFieldMapping',
      component: IntegrationFieldMapping,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-field-mapping/new',
      name: 'IntegrationFieldMappingCreate',
      component: IntegrationFieldMappingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-field-mapping/:integrationFieldMappingId/edit',
      name: 'IntegrationFieldMappingEdit',
      component: IntegrationFieldMappingUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'integration-field-mapping/:integrationFieldMappingId/view',
      name: 'IntegrationFieldMappingView',
      component: IntegrationFieldMappingDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
