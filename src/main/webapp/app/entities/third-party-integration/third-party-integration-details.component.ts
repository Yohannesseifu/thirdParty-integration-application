import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import ThirdPartyIntegrationService from './third-party-integration.service';
import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ThirdPartyIntegrationDetails',
  setup() {
    const thirdPartyIntegrationService = inject('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const thirdPartyIntegration: Ref<IThirdPartyIntegration> = ref({});

    const retrieveThirdPartyIntegration = async thirdPartyIntegrationId => {
      try {
        const res = await thirdPartyIntegrationService().find(thirdPartyIntegrationId);
        thirdPartyIntegration.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.thirdPartyIntegrationId) {
      retrieveThirdPartyIntegration(route.params.thirdPartyIntegrationId);
    }

    return {
      alertService,
      thirdPartyIntegration,

      previousState,
    };
  },
});
