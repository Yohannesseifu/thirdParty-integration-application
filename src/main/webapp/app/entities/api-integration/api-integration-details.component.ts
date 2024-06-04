import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import ApiIntegrationService from './api-integration.service';
import { type IApiIntegration } from '@/shared/model/api-integration.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApiIntegrationDetails',
  setup() {
    const apiIntegrationService = inject('apiIntegrationService', () => new ApiIntegrationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const apiIntegration: Ref<IApiIntegration> = ref({});

    const retrieveApiIntegration = async apiIntegrationId => {
      try {
        const res = await apiIntegrationService().find(apiIntegrationId);
        apiIntegration.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.apiIntegrationId) {
      retrieveApiIntegration(route.params.apiIntegrationId);
    }

    return {
      alertService,
      apiIntegration,

      previousState,
    };
  },
});
