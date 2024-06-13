import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import ApiIntegrationService from './api-integration.service';
import { type IApiIntegration } from '@/shared/model/api-integration.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApiIntegration',
  setup() {
    const apiIntegrationService = inject('apiIntegrationService', () => new ApiIntegrationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const apiIntegrations: Ref<IApiIntegration[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveApiIntegrations = async () => {
      isFetching.value = true;
      try {
        const res = await apiIntegrationService().retrieve();
        apiIntegrations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveApiIntegrations();
    };

    onMounted(async () => {
      await retrieveApiIntegrations();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IApiIntegration) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeApiIntegration = async () => {
      try {
        await apiIntegrationService().delete(removeId.value);
        const message = 'A ApiIntegration is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveApiIntegrations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      apiIntegrations,
      handleSyncList,
      isFetching,
      retrieveApiIntegrations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeApiIntegration,
    };
  },
});
