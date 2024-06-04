import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import MenuService from './menu.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ThirdPartyIntegrationService from '@/entities/third-party-integration/third-party-integration.service';
import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import { type IMenu, Menu } from '@/shared/model/menu.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'MenuUpdate',
  setup() {
    const menuService = inject('menuService', () => new MenuService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const menu: Ref<IMenu> = ref(new Menu());

    const menus: Ref<IMenu[]> = ref([]);

    const thirdPartyIntegrationService = inject('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());

    const thirdPartyIntegrations: Ref<IThirdPartyIntegration[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      menuService()
        .retrieve()
        .then(res => {
          menus.value = res.data;
        });
      thirdPartyIntegrationService()
        .retrieve()
        .then(res => {
          thirdPartyIntegrations.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      menuName: {},
      menuDescription: {},
      iconPath: {},
      enabled: {},
      dynamicPaymentMenus: {},
      parent: {},
    };
    const v$ = useVuelidate(validationRules, menu as any);
    v$.value.$validate();

    return {
      menuService,
      alertService,
      menu,
      previousState,
      isSaving,
      currentLanguage,
      menus,
      thirdPartyIntegrations,
      v$,
    };
  },
  created(): void {
    this.menu.dynamicPaymentMenus = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.menu.id) {
        this.menuService()
          .update(this.menu)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Menu is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.menuService()
          .create(this.menu)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Menu is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option, pkField = 'id'): any {
      if (selectedVals) {
        return selectedVals.find(value => option[pkField] === value[pkField]) ?? option;
      }
      return option;
    },
  },
});
