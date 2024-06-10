import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import RequestInputService from './request-input.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import OperationService from '@/entities/operation/operation.service';
import { type IOperation } from '@/shared/model/operation.model';
import { type IRequestInput, RequestInput } from '@/shared/model/request-input.model';
import { RequestInputType } from '@/shared/model/enumerations/request-input-type.model';
import { DataType } from '@/shared/model/enumerations/data-type.model';
import { AutoUserValue } from '@/shared/model/enumerations/auto-user-value.model';
import { TransactionParams } from '@/shared/model/enumerations/transaction-params.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RequestInputUpdate',
  setup() {
    const requestInputService = inject('requestInputService', () => new RequestInputService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const requestInput: Ref<IRequestInput> = ref(new RequestInput());

    const operationService = inject('operationService', () => new OperationService());

    const operations: Ref<IOperation[]> = ref([]);
    const requestInputTypeValues: Ref<string[]> = ref(Object.keys(RequestInputType));
    const dataTypeValues: Ref<string[]> = ref(Object.keys(DataType));
    const autoUserValueValues: Ref<string[]> = ref(Object.keys(AutoUserValue));
    const transactionParamsValues: Ref<string[]> = ref(Object.keys(TransactionParams));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveRequestInput = async requestInputId => {
      try {
        const res = await requestInputService().find(requestInputId);
        requestInput.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.requestInputId) {
      retrieveRequestInput(route.params.requestInputId);
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
      inputName: {
        required: validations.required('This field is required.'),
      },
      inputType: {
        required: validations.required('This field is required.'),
      },
      dataType: {
        required: validations.required('This field is required.'),
      },
      testValue: {},
      defaultValue: {},
      autoUserValue: {},
      isEncoded: {},
      maxLength: {},
      minLength: {},
      minValue: {},
      maxValue: {},
      validationPattern: {},
      isRequired: {},
      valueFromTransaction: {},
      operation: {},
    };
    const v$ = useVuelidate(validationRules, requestInput as any);
    v$.value.$validate();

    return {
      requestInputService,
      alertService,
      requestInput,
      previousState,
      requestInputTypeValues,
      dataTypeValues,
      autoUserValueValues,
      transactionParamsValues,
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
      if (this.requestInput.id) {
        this.requestInputService()
          .update(this.requestInput)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A RequestInput is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.requestInputService()
          .create(this.requestInput)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A RequestInput is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
