import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import IntegrationFieldMappingService from './integration-field-mapping.service';
import { type IIntegrationFieldMapping } from '@/shared/model/integration-field-mapping.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntegrationFieldMapping',
  setup() {
    const integrationFieldMappingService = inject('integrationFieldMappingService', () => new IntegrationFieldMappingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const integrationFieldMappings: Ref<IIntegrationFieldMapping[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveIntegrationFieldMappings = async () => {
      isFetching.value = true;
      try {
        const res = await integrationFieldMappingService().retrieve();
        integrationFieldMappings.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveIntegrationFieldMappings();
    };

    onMounted(async () => {
      await retrieveIntegrationFieldMappings();
    });

    const removeId: Ref<string> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IIntegrationFieldMapping) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeIntegrationFieldMapping = async () => {
      try {
        await integrationFieldMappingService().delete(removeId.value);
        const message = 'A IntegrationFieldMapping is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveIntegrationFieldMappings();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      integrationFieldMappings,
      handleSyncList,
      isFetching,
      retrieveIntegrationFieldMappings,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeIntegrationFieldMapping,
    };
  },
});
