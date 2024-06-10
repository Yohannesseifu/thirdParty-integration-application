import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import PaymentDetailService from './payment-detail.service';
import { type IPaymentDetail } from '@/shared/model/payment-detail.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentDetail',
  setup() {
    const paymentDetailService = inject('paymentDetailService', () => new PaymentDetailService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const paymentDetails: Ref<IPaymentDetail[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePaymentDetails = async () => {
      isFetching.value = true;
      try {
        const res = await paymentDetailService().retrieve();
        paymentDetails.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePaymentDetails();
    };

    onMounted(async () => {
      await retrievePaymentDetails();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPaymentDetail) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePaymentDetail = async () => {
      try {
        await paymentDetailService().delete(removeId.value);
        const message = 'A PaymentDetail is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePaymentDetails();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      paymentDetails,
      handleSyncList,
      isFetching,
      retrievePaymentDetails,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePaymentDetail,
    };
  },
});
