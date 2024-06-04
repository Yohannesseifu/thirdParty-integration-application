import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import FieldService from './field.service';
import { type IField } from '@/shared/model/field.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FieldDetails',
  setup() {
    const fieldService = inject('fieldService', () => new FieldService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const field: Ref<IField> = ref({});

    const retrieveField = async fieldId => {
      try {
        const res = await fieldService().find(fieldId);
        field.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fieldId) {
      retrieveField(route.params.fieldId);
    }

    return {
      alertService,
      field,

      previousState,
    };
  },
});
