import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import FormUiService from './form-ui.service';
import { type IFormUi } from '@/shared/model/form-ui.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FormUi',
  setup() {
    const formUiService = inject('formUiService', () => new FormUiService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const formUis: Ref<IFormUi[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFormUis = async () => {
      isFetching.value = true;
      try {
        const res = await formUiService().retrieve();
        formUis.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFormUis();
    };

    onMounted(async () => {
      await retrieveFormUis();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IFormUi) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeFormUi = async () => {
      try {
        await formUiService().delete(removeId.value);
        const message = 'A FormUi is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFormUis();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      formUis,
      handleSyncList,
      isFetching,
      retrieveFormUis,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeFormUi,
    };
  },
});
