import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import ApiRequestService from './api-request.service';
import { type IApiRequest } from '@/shared/model/api-request.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApiRequest',
  setup() {
    const apiRequestService = inject('apiRequestService', () => new ApiRequestService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const apiRequests: Ref<IApiRequest[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveApiRequests = async () => {
      isFetching.value = true;
      try {
        const res = await apiRequestService().retrieve();
        apiRequests.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveApiRequests();
    };

    onMounted(async () => {
      await retrieveApiRequests();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IApiRequest) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeApiRequest = async () => {
      try {
        await apiRequestService().delete(removeId.value);
        const message = 'A ApiRequest is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveApiRequests();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      apiRequests,
      handleSyncList,
      isFetching,
      retrieveApiRequests,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeApiRequest,
    };
  },
});
