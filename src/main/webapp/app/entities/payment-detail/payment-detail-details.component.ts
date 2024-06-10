import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import PaymentDetailService from './payment-detail.service';
import { type IPaymentDetail } from '@/shared/model/payment-detail.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentDetailDetails',
  setup() {
    const paymentDetailService = inject('paymentDetailService', () => new PaymentDetailService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const paymentDetail: Ref<IPaymentDetail> = ref({});

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

    return {
      alertService,
      paymentDetail,

      previousState,
    };
  },
});
