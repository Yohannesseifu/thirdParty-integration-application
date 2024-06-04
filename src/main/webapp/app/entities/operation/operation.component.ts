import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import OperationService from './operation.service';
import { type IOperation } from '@/shared/model/operation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Operation',
  setup() {
    const operationService = inject('operationService', () => new OperationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const operations: Ref<IOperation[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveOperations = async () => {
      isFetching.value = true;
      try {
        const res = await operationService().retrieve();
        operations.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveOperations();
    };

    onMounted(async () => {
      await retrieveOperations();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IOperation) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeOperation = async () => {
      try {
        await operationService().delete(removeId.value);
        const message = 'A Operation is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveOperations();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      operations,
      handleSyncList,
      isFetching,
      retrieveOperations,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeOperation,
    };
  },
});
