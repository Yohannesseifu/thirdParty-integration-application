import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PaymentDetailService from './payment-detail.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ApiRequestService from '@/entities/api-request/api-request.service';
import { type IApiRequest } from '@/shared/model/api-request.model';
import OperationService from '@/entities/operation/operation.service';
import { type IOperation } from '@/shared/model/operation.model';
import { type IPaymentDetail, PaymentDetail } from '@/shared/model/payment-detail.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentDetailUpdate',
  setup() {
    const paymentDetailService = inject('paymentDetailService', () => new PaymentDetailService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const paymentDetail: Ref<IPaymentDetail> = ref(new PaymentDetail());

    const apiRequestService = inject('apiRequestService', () => new ApiRequestService());

    const apiRequests: Ref<IApiRequest[]> = ref([]);

    const operationService = inject('operationService', () => new OperationService());

    const operations: Ref<IOperation[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePaymentDetail = async paymentDetailId => {
      try {
        const res = await paymentDetailService().find(paymentDetailId);
        paymentDetail.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.paymentDetailId) {
      retrievePaymentDetail(route.params.paymentDetailId);
    }

    const initRelationships = () => {
      apiRequestService()
        .retrieve()
        .then(res => {
          apiRequests.value = res.data;
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
      computedPaymentDetail: {
        maxLength: validations.maxLength('This field cannot be longer than 2000 characters.', 2000),
      },
      apiRequest: {},
      operation: {},
    };
    const v$ = useVuelidate(validationRules, paymentDetail as any);
    v$.value.$validate();

    return {
      paymentDetailService,
      alertService,
      paymentDetail,
      previousState,
      isSaving,
      currentLanguage,
      apiRequests,
      operations,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.paymentDetail.id) {
        this.paymentDetailService()
          .update(this.paymentDetail)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A PaymentDetail is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.paymentDetailService()
          .create(this.paymentDetail)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A PaymentDetail is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
