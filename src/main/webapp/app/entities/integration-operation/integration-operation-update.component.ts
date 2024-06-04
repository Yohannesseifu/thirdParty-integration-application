import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import IntegrationOperationService from './integration-operation.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ThirdPartyIntegrationService from '@/entities/third-party-integration/third-party-integration.service';
import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import OperationService from '@/entities/operation/operation.service';
import { type IOperation } from '@/shared/model/operation.model';
import { type IIntegrationOperation, IntegrationOperation } from '@/shared/model/integration-operation.model';
import { OperationType } from '@/shared/model/enumerations/operation-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntegrationOperationUpdate',
  setup() {
    const integrationOperationService = inject('integrationOperationService', () => new IntegrationOperationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const integrationOperation: Ref<IIntegrationOperation> = ref(new IntegrationOperation());

    const thirdPartyIntegrationService = inject('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());

    const thirdPartyIntegrations: Ref<IThirdPartyIntegration[]> = ref([]);

    const operationService = inject('operationService', () => new OperationService());

    const operations: Ref<IOperation[]> = ref([]);
    const operationTypeValues: Ref<string[]> = ref(Object.keys(OperationType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveIntegrationOperation = async integrationOperationId => {
      try {
        const res = await integrationOperationService().find(integrationOperationId);
        integrationOperation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.integrationOperationId) {
      retrieveIntegrationOperation(route.params.integrationOperationId);
    }

    const initRelationships = () => {
      thirdPartyIntegrationService()
        .retrieve()
        .then(res => {
          thirdPartyIntegrations.value = res.data;
        });
      operationService()
        .retrieve()
        .then(res => {
          operations.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      operationType: {},
      thirdPartyIntegration: {},
      operation: {},
    };
    const v$ = useVuelidate(validationRules, integrationOperation as any);
    v$.value.$validate();

    return {
      integrationOperationService,
      alertService,
      integrationOperation,
      previousState,
      operationTypeValues,
      isSaving,
      currentLanguage,
      thirdPartyIntegrations,
      operations,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.integrationOperation.id) {
        this.integrationOperationService()
          .update(this.integrationOperation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A IntegrationOperation is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.integrationOperationService()
          .create(this.integrationOperation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A IntegrationOperation is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
