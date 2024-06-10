import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import HeaderService from './header.service';
import { type IHeader } from '@/shared/model/header.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Header',
  setup() {
    const headerService = inject('headerService', () => new HeaderService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const headers: Ref<IHeader[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveHeaders = async () => {
      isFetching.value = true;
      try {
        const res = await headerService().retrieve();
        headers.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveHeaders();
    };

    onMounted(async () => {
      await retrieveHeaders();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IHeader) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeHeader = async () => {
      try {
        await headerService().delete(removeId.value);
        const message = 'A Header is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveHeaders();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      headers,
      handleSyncList,
      isFetching,
      retrieveHeaders,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeHeader,
    };
  },
});
