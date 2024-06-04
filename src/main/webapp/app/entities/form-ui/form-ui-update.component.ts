import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FormUiService from './form-ui.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ThirdPartyIntegrationService from '@/entities/third-party-integration/third-party-integration.service';
import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import { type IFormUi, FormUi } from '@/shared/model/form-ui.model';
import { FormType } from '@/shared/model/enumerations/form-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FormUiUpdate',
  setup() {
    const formUiService = inject('formUiService', () => new FormUiService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const formUi: Ref<IFormUi> = ref(new FormUi());

    const thirdPartyIntegrationService = inject('thirdPartyIntegrationService', () => new ThirdPartyIntegrationService());

    const thirdPartyIntegrations: Ref<IThirdPartyIntegration[]> = ref([]);
    const formTypeValues: Ref<string[]> = ref(Object.keys(FormType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      thirdPartyIntegrationService()
        .retrieve()
        .then(res => {
          thirdPartyIntegrations.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      formName: {},
      formDescription: {},
      formType: {},
      stepOrder: {},
      thirdPartyIntegration: {},
    };
    const v$ = useVuelidate(validationRules, formUi as any);
    v$.value.$validate();

    return {
      formUiService,
      alertService,
      formUi,
      previousState,
      formTypeValues,
      isSaving,
      currentLanguage,
      thirdPartyIntegrations,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.formUi.id) {
        this.formUiService()
          .update(this.formUi)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A FormUi is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.formUiService()
          .create(this.formUi)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A FormUi is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
