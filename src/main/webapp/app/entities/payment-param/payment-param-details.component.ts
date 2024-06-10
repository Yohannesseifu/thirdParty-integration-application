import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import PaymentParamService from './payment-param.service';
import { type IPaymentParam } from '@/shared/model/payment-param.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentParamDetails',
  setup() {
    const paymentParamService = inject('paymentParamService', () => new PaymentParamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const paymentParam: Ref<IPaymentParam> = ref({});

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

    return {
      alertService,
      paymentParam,

      previousState,
    };
  },
});
