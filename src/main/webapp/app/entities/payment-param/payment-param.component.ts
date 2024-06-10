import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import PaymentParamService from './payment-param.service';
import { type IPaymentParam } from '@/shared/model/payment-param.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PaymentParam',
  setup() {
    const paymentParamService = inject('paymentParamService', () => new PaymentParamService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const paymentParams: Ref<IPaymentParam[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePaymentParams = async () => {
      isFetching.value = true;
      try {
        const res = await paymentParamService().retrieve();
        paymentParams.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePaymentParams();
    };

    onMounted(async () => {
      await retrievePaymentParams();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPaymentParam) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePaymentParam = async () => {
      try {
        await paymentParamService().delete(removeId.value);
        const message = 'A PaymentParam is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePaymentParams();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      paymentParams,
      handleSyncList,
      isFetching,
      retrievePaymentParams,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePaymentParam,
    };
  },
});
