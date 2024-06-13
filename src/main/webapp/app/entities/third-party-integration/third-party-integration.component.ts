import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import ThirdPartyIntegrationService from './third-party-integration.service';
import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ThirdPartyIntegration',
  setup() {
    const thirdPartyIntegrationService = inject('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const thirdPartyIntegrations: Ref<IThirdPartyIntegration[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveThirdPartyIntegrations = async () => {
      isFetching.value = true;
      try {
        const res = await thirdPartyIntegrationService().retrieve();
        thirdPartyIntegrations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveThirdPartyIntegrations();
    };

    onMounted(async () => {
      await retrieveThirdPartyIntegrations();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IThirdPartyIntegration) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeThirdPartyIntegration = async () => {
      try {
        await thirdPartyIntegrationService().delete(removeId.value);
        const message = 'A ThirdPartyIntegration is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveThirdPartyIntegrations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      thirdPartyIntegrations,
      handleSyncList,
      isFetching,
      retrieveThirdPartyIntegrations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeThirdPartyIntegration,
    };
  },
});
