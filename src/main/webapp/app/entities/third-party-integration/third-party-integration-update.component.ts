import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ThirdPartyIntegrationService from './third-party-integration.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import MenuService from '@/entities/menu/menu.service';
import { type IMenu } from '@/shared/model/menu.model';
import { type IThirdPartyIntegration, ThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import { IntegrationType } from '@/shared/model/enumerations/integration-type.model';
import { Visiblity } from '@/shared/model/enumerations/visiblity.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ThirdPartyIntegrationUpdate',
  setup() {
    const thirdPartyIntegrationService = inject('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const thirdPartyIntegration: Ref<IThirdPartyIntegration> = ref(new ThirdPartyIntegration());

    const menuService = inject('menuService', () => new MenuService());

    const menus: Ref<IMenu[]> = ref([]);
    const integrationTypeValues: Ref<string[]> = ref(Object.keys(IntegrationType));
    const visiblityValues: Ref<string[]> = ref(Object.keys(Visiblity));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveThirdPartyIntegration = async thirdPartyIntegrationId => {
      try {
        const res = await thirdPartyIntegrationService().find(thirdPartyIntegrationId);
        thirdPartyIntegration.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.thirdPartyIntegrationId) {
      retrieveThirdPartyIntegration(route.params.thirdPartyIntegrationId);
    }

    const initRelationships = () => {
      menuService()
        .retrieve()
        .then(res => {
          menus.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      isDraft: {},
      integrationName: {},
      companyName: {},
      description: {},
      iconPath: {},
      enabled: {},
      accountNumber: {},
      minimumAmount: {},
      maximumAmount: {},
      currencyCode: {},
      paymentConfirmationTemplate: {},
      paymentDetailTemplate: {},
      integrationCategory: {},
      visiblity: {},
      confirmRecipientIdentity: {},
      categoryMenus: {},
    };
    const v$ = useVuelidate(validationRules, thirdPartyIntegration as any);
    v$.value.$validate();

    return {
      thirdPartyIntegrationService,
      alertService,
      thirdPartyIntegration,
      previousState,
      integrationTypeValues,
      visiblityValues,
      isSaving,
      currentLanguage,
      menus,
      v$,
    };
  },
  created(): void {
    this.thirdPartyIntegration.categoryMenus = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.thirdPartyIntegration.id) {
        this.thirdPartyIntegrationService()
          .update(this.thirdPartyIntegration)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A ThirdPartyIntegration is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.thirdPartyIntegrationService()
          .create(this.thirdPartyIntegration)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A ThirdPartyIntegration is created with identifier ' + param.id);
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
