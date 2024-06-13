import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import OperationService from './operation.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ApiIntegrationService from '@/entities/api-integration/api-integration.service';
import { type IApiIntegration } from '@/shared/model/api-integration.model';
import { type IOperation, Operation } from '@/shared/model/operation.model';
import { HttpMethod } from '@/shared/model/enumerations/http-method.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperationUpdate',
  setup() {
    const operationService = inject('operationService', () => new OperationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const operation: Ref<IOperation> = ref(new Operation());

    const apiIntegrationService = inject('apiIntegrationService', () => new ApiIntegrationService());

    const apiIntegrations: Ref<IApiIntegration[]> = ref([]);
    const httpMethodValues: Ref<string[]> = ref(Object.keys(HttpMethod));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveOperation = async operationId => {
      try {
        const res = await operationService().find(operationId);
        operation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.operationId) {
      retrieveOperation(route.params.operationId);
    }

    const initRelationships = () => {
      apiIntegrationService()
        .retrieve()
        .then(res => {
          apiIntegrations.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      operationName: {
        required: validations.required('This field is required.'),
      },
      httpMethod: {
        required: validations.required('This field is required.'),
      },
      endpointPath: {
        required: validations.required('This field is required.'),
      },
      requestBodyTemplate: {},
      apiIntegration: {},
    };
    const v$ = useVuelidate(validationRules, operation as any);
    v$.value.$validate();

    return {
      operationService,
      alertService,
      operation,
      previousState,
      httpMethodValues,
      isSaving,
      currentLanguage,
      apiIntegrations,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.operation.id) {
        this.operationService()
          .update(this.operation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Operation is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.operationService()
          .create(this.operation)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Operation is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
