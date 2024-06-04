import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import IntegrationOperationService from './integration-operation.service';
import { type IIntegrationOperation } from '@/shared/model/integration-operation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntegrationOperation',
  setup() {
    const integrationOperationService = inject('integrationOperationService', () => new IntegrationOperationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const integrationOperations: Ref<IIntegrationOperation[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveIntegrationOperations = async () => {
      isFetching.value = true;
      try {
        const res = await integrationOperationService().retrieve();
        integrationOperations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveIntegrationOperations();
    };

    onMounted(async () => {
      await retrieveIntegrationOperations();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IIntegrationOperation) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeIntegrationOperation = async () => {
      try {
        await integrationOperationService().delete(removeId.value);
        const message = 'A IntegrationOperation is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveIntegrationOperations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      integrationOperations,
      handleSyncList,
      isFetching,
      retrieveIntegrationOperations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeIntegrationOperation,
    };
  },
});
