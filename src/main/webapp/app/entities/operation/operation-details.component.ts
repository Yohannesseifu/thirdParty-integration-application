import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import OperationService from './operation.service';
import { type IOperation } from '@/shared/model/operation.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'OperationDetails',
  setup() {
    const operationService = inject('operationService', () => new OperationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const operation: Ref<IOperation> = ref({});

    const retrieveOperation = async operationId => {
      try {
        const res = await operationService().find(operationId);
        operation.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.operationId) {
      retrieveOperation(route.params.operationId);
    }

    return {
      alertService,
      operation,

      previousState,
    };
  },
});
