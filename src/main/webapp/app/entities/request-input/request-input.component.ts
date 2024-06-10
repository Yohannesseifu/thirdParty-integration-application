import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import RequestInputService from './request-input.service';
import { type IRequestInput } from '@/shared/model/request-input.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RequestInput',
  setup() {
    const requestInputService = inject('requestInputService', () => new RequestInputService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const requestInputs: Ref<IRequestInput[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveRequestInputs = async () => {
      isFetching.value = true;
      try {
        const res = await requestInputService().retrieve();
        requestInputs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveRequestInputs();
    };

    onMounted(async () => {
      await retrieveRequestInputs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IRequestInput) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeRequestInput = async () => {
      try {
        await requestInputService().delete(removeId.value);
        const message = 'A RequestInput is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveRequestInputs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      requestInputs,
      handleSyncList,
      isFetching,
      retrieveRequestInputs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeRequestInput,
    };
  },
});
