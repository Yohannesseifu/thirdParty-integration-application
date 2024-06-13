import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ResponseOutputService from './response-output.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OperationService from '@/entities/operation/operation.service';
import { type IOperation } from '@/shared/model/operation.model';
import { type IResponseOutput, ResponseOutput } from '@/shared/model/response-output.model';
import { DataType } from '@/shared/model/enumerations/data-type.model';
import { Scope } from '@/shared/model/enumerations/scope.model';
import { CoreTransferParams } from '@/shared/model/enumerations/core-transfer-params.model';
import { LogicalOperator } from '@/shared/model/enumerations/logical-operator.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ResponseOutputUpdate',
  setup() {
    const responseOutputService = inject('responseOutputService', () => new ResponseOutputService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const responseOutput: Ref<IResponseOutput> = ref(new ResponseOutput());

    const operationService = inject('operationService', () => new OperationService());

    const operations: Ref<IOperation[]> = ref([]);
    const dataTypeValues: Ref<string[]> = ref(Object.keys(DataType));
    const scopeValues: Ref<string[]> = ref(Object.keys(Scope));
    const coreTransferParamsValues: Ref<string[]> = ref(Object.keys(CoreTransferParams));
    const logicalOperatorValues: Ref<string[]> = ref(Object.keys(LogicalOperator));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveResponseOutput = async responseOutputId => {
      try {
        const res = await responseOutputService().find(responseOutputId);
        responseOutput.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.responseOutputId) {
      retrieveResponseOutput(route.params.responseOutputId);
    }

    const initRelationships = () => {
      operationService()
        .retrieve()
        .then(res => {
          operations.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      outputName: {
        required: validations.required('This field is required.'),
      },
      dataType: {
        required: validations.required('This field is required.'),
      },
      responseValuePath: {
        required: validations.required('This field is required.'),
      },
      responseScope: {
        required: validations.required('This field is required.'),
      },
      transferCoreMapping: {},
      isLogicField: {},
      constantValueToCompare: {},
      operatorToCompareValue: {},
      isRequired: {},
      operation: {},
    };
    const v$ = useVuelidate(validationRules, responseOutput as any);
    v$.value.$validate();

    return {
      responseOutputService,
      alertService,
      responseOutput,
      previousState,
      dataTypeValues,
      scopeValues,
      coreTransferParamsValues,
      logicalOperatorValues,
      isSaving,
      currentLanguage,
      operations,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.responseOutput.id) {
        this.responseOutputService()
          .update(this.responseOutput)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A ResponseOutput is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.responseOutputService()
          .create(this.responseOutput)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A ResponseOutput is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
