import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import FieldService from './field.service';
import { type IField } from '@/shared/model/field.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Field',
  setup() {
    const fieldService = inject('fieldService', () => new FieldService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fields: Ref<IField[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveFields = async () => {
      isFetching.value = true;
      try {
        const res = await fieldService().retrieve();
        fields.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveFields();
    };

    onMounted(async () => {
      await retrieveFields();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IField) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeField = async () => {
      try {
        await fieldService().delete(removeId.value);
        const message = 'A Field is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveFields();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      fields,
      handleSyncList,
      isFetching,
      retrieveFields,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeField,
    };
  },
});
