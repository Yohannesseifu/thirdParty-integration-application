import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import RequestInputService from './request-input.service';
import { type IRequestInput } from '@/shared/model/request-input.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'RequestInputDetails',
  setup() {
    const requestInputService = inject('requestInputService', () => new RequestInputService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const requestInput: Ref<IRequestInput> = ref({});

    const retrieveRequestInput = async requestInputId => {
      try {
        const res = await requestInputService().find(requestInputId);
        requestInput.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.requestInputId) {
      retrieveRequestInput(route.params.requestInputId);
    }

    return {
      alertService,
      requestInput,

      previousState,
    };
  },
});
