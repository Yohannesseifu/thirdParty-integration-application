import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import IntegrationFieldMappingService from './integration-field-mapping.service';
import { type IIntegrationFieldMapping } from '@/shared/model/integration-field-mapping.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntegrationFieldMappingDetails',
  setup() {
    const integrationFieldMappingService = inject('integrationFieldMappingService', () => new IntegrationFieldMappingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const integrationFieldMapping: Ref<IIntegrationFieldMapping> = ref({});

    const retrieveIntegrationFieldMapping = async integrationFieldMappingId => {
      try {
        const res = await integrationFieldMappingService().find(integrationFieldMappingId);
        integrationFieldMapping.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.integrationFieldMappingId) {
      retrieveIntegrationFieldMapping(route.params.integrationFieldMappingId);
    }

    return {
      alertService,
      integrationFieldMapping,

      previousState,
    };
  },
});
