import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import FormUiService from './form-ui.service';
import { type IFormUi } from '@/shared/model/form-ui.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FormUiDetails',
  setup() {
    const formUiService = inject('formUiService', () => new FormUiService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const formUi: Ref<IFormUi> = ref({});

    const retrieveFormUi = async formUiId => {
      try {
        const res = await formUiService().find(formUiId);
        formUi.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.formUiId) {
      retrieveFormUi(route.params.formUiId);
    }

    return {
      alertService,
      formUi,

      previousState,
    };
  },
});
