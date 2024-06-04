import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import FieldUIMetaDataService from './field-ui-meta-data.service';
import { type IFieldUIMetaData } from '@/shared/model/field-ui-meta-data.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FieldUIMetaData',
  setup() {
    const fieldUIMetaDataService = inject('fieldUIMetaDataService', () => new FieldUIMetaDataService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fieldUIMetaData: Ref<IFieldUIMetaData[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFieldUIMetaDatas = async () => {
      isFetching.value = true;
      try {
        const res = await fieldUIMetaDataService().retrieve();
        fieldUIMetaData.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFieldUIMetaDatas();
    };

    onMounted(async () => {
      await retrieveFieldUIMetaDatas();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IFieldUIMetaData) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeFieldUIMetaData = async () => {
      try {
        await fieldUIMetaDataService().delete(removeId.value);
        const message = 'A FieldUIMetaData is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFieldUIMetaDatas();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      fieldUIMetaData,
      handleSyncList,
      isFetching,
      retrieveFieldUIMetaDatas,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeFieldUIMetaData,
    };
  },
});
