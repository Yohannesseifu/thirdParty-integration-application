import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PaymentParamService from './payment-param.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PaymentDetailService from '@/entities/payment-detail/payment-detail.service';
import { type IPaymentDetail } from '@/shared/model/payment-detail.model';
import { type IPaymentParam, PaymentParam } from '@/shared/model/payment-param.model';
import { PaymentParamType } from '@/shared/model/enumerations/payment-param-type.model';
import { DataType } from '@/shared/model/enumerations/data-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentParamUpdate',
  setup() {
    const paymentParamService = inject('paymentParamService', () => new PaymentParamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const paymentParam: Ref<IPaymentParam> = ref(new PaymentParam());

    const paymentDetailService = inject('paymentDetailService', () => new PaymentDetailService());

    const paymentDetails: Ref<IPaymentDetail[]> = ref([]);
    const paymentParamTypeValues: Ref<string[]> = ref(Object.keys(PaymentParamType));
    const dataTypeValues: Ref<string[]> = ref(Object.keys(DataType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePaymentParam = async paymentParamId => {
      try {
        const res = await paymentParamService().find(paymentParamId);
        paymentParam.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.paymentParamId) {
      retrievePaymentParam(route.params.paymentParamId);
    }

    const initRelationships = () => {
      paymentDetailService()
        .retrieve()
        .then(res => {
          paymentDetails.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      type: {},
      name: {},
      valueStr: {},
      dataType: {
        required: validations.required('This field is required.'),
      },
      paymentDetail: {},
    };
    const v$ = useVuelidate(validationRules, paymentParam as any);
    v$.value.$validate();

    return {
      paymentParamService,
      alertService,
      paymentParam,
      previousState,
      paymentParamTypeValues,
      dataTypeValues,
      isSaving,
      currentLanguage,
      paymentDetails,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.paymentParam.id) {
        this.paymentParamService()
          .update(this.paymentParam)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A PaymentParam is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.paymentParamService()
          .create(this.paymentParam)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A PaymentParam is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
