import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import MenuService from './menu.service';
import { type IMenu } from '@/shared/model/menu.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MenuDetails',
  setup() {
    const menuService = inject('menuService', () => new MenuService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const menu: Ref<IMenu> = ref({});

    const retrieveMenu = async menuId => {
      try {
        const res = await menuService().find(menuId);
        menu.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.menuId) {
      retrieveMenu(route.params.menuId);
    }

    return {
      alertService,
      menu,

      previousState,
    };
  },
});
