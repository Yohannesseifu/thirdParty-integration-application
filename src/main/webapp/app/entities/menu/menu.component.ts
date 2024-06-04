import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';

import MenuService from './menu.service';
import { type IMenu } from '@/shared/model/menu.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Menu',
  setup() {
    const menuService = inject('menuService', () => new MenuService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const menus: Ref<IMenu[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveMenus = async () => {
      isFetching.value = true;
      try {
        const res = await menuService().retrieve();
        menus.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveMenus();
    };

    onMounted(async () => {
      await retrieveMenus();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IMenu) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeMenu = async () => {
      try {
        await menuService().delete(removeId.value);
        const message = 'A Menu is deleted with identifier ' + removeId.value;
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveMenus();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      menus,
      handleSyncList,
      isFetching,
      retrieveMenus,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeMenu,
    };
  },
});
