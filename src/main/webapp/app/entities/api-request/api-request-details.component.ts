import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import ApiRequestService from './api-request.service';
import { type IApiRequest } from '@/shared/model/api-request.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApiRequestDetails',
  setup() {
    const apiRequestService = inject('apiRequestService', () => new ApiRequestService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const apiRequest: Ref<IApiRequest> = ref({});

    const retrieveApiRequest = async apiRequestId => {
      try {
        const res = await apiRequestService().find(apiRequestId);
        apiRequest.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.apiRequestId) {
      retrieveApiRequest(route.params.apiRequestId);
    }

    return {
      alertService,
      apiRequest,

      previousState,
    };
  },
});
