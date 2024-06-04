import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import ResponseOutputService from './response-output.service';
import { type IResponseOutput } from '@/shared/model/response-output.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ResponseOutputDetails',
  setup() {
    const responseOutputService = inject('responseOutputService', () => new ResponseOutputService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const responseOutput: Ref<IResponseOutput> = ref({});

    const retrieveResponseOutput = async responseOutputId => {
      try {
        const res = await responseOutputService().find(responseOutputId);
        responseOutput.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.responseOutputId) {
      retrieveResponseOutput(route.params.responseOutputId);
    }

    return {
      alertService,
      responseOutput,

      previousState,
    };
  },
});
