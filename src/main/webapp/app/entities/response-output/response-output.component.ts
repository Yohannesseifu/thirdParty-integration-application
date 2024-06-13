import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import ResponseOutputService from './response-output.service';
import { type IResponseOutput } from '@/shared/model/response-output.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ResponseOutput',
  setup() {
    const responseOutputService = inject('responseOutputService', () => new ResponseOutputService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const responseOutputs: Ref<IResponseOutput[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveResponseOutputs = async () => {
      isFetching.value = true;
      try {
        const res = await responseOutputService().retrieve();
        responseOutputs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveResponseOutputs();
    };

    onMounted(async () => {
      await retrieveResponseOutputs();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IResponseOutput) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeResponseOutput = async () => {
      try {
        await responseOutputService().delete(removeId.value);
        const message = 'A ResponseOutput is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveResponseOutputs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      responseOutputs,
      handleSyncList,
      isFetching,
      retrieveResponseOutputs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeResponseOutput,
    };
  },
});
