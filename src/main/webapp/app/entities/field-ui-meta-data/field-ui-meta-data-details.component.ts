import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import FieldUIMetaDataService from './field-ui-meta-data.service';
import { type IFieldUIMetaData } from '@/shared/model/field-ui-meta-data.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FieldUIMetaDataDetails',
  setup() {
    const fieldUIMetaDataService = inject('fieldUIMetaDataService', () => new FieldUIMetaDataService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const fieldUIMetaData: Ref<IFieldUIMetaData> = ref({});

    const retrieveFieldUIMetaData = async fieldUIMetaDataId => {
      try {
        const res = await fieldUIMetaDataService().find(fieldUIMetaDataId);
        fieldUIMetaData.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fieldUIMetaDataId) {
      retrieveFieldUIMetaData(route.params.fieldUIMetaDataId);
    }

    return {
      alertService,
      fieldUIMetaData,

      previousState,
    };
  },
});
