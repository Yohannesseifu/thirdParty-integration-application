import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import HeaderService from './header.service';
import { type IHeader } from '@/shared/model/header.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HeaderDetails',
  setup() {
    const headerService = inject('headerService', () => new HeaderService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const header: Ref<IHeader> = ref({});

    const retrieveHeader = async headerId => {
      try {
        const res = await headerService().find(headerId);
        header.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.headerId) {
      retrieveHeader(route.params.headerId);
    }

    return {
      alertService,
      header,

      previousState,
    };
  },
});
