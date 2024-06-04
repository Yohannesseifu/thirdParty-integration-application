import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import IntegrationOperationService from './integration-operation.service';
import { type IIntegrationOperation } from '@/shared/model/integration-operation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntegrationOperationDetails',
  setup() {
    const integrationOperationService = inject('integrationOperationService', () => new IntegrationOperationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const integrationOperation: Ref<IIntegrationOperation> = ref({});

    const retrieveIntegrationOperation = async integrationOperationId => {
      try {
        const res = await integrationOperationService().find(integrationOperationId);
        integrationOperation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.integrationOperationId) {
      retrieveIntegrationOperation(route.params.integrationOperationId);
    }

    return {
      alertService,
      integrationOperation,

      previousState,
    };
  },
});
