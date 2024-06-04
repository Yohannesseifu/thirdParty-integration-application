import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ApiIntegrationService from './api-integration.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IApiIntegration, ApiIntegration } from '@/shared/model/api-integration.model';
import { ContentType } from '@/shared/model/enumerations/content-type.model';
import { AuthType } from '@/shared/model/enumerations/auth-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApiIntegrationUpdate',
  setup() {
    const apiIntegrationService = inject('apiIntegrationService', () => new ApiIntegrationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const apiIntegration: Ref<IApiIntegration> = ref(new ApiIntegration());
    const contentTypeValues: Ref<string[]> = ref(Object.keys(ContentType));
    const authTypeValues: Ref<string[]> = ref(Object.keys(AuthType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveApiIntegration = async apiIntegrationId => {
      try {
        const res = await apiIntegrationService().find(apiIntegrationId);
        apiIntegration.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.apiIntegrationId) {
      retrieveApiIntegration(route.params.apiIntegrationId);
    }

    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required('This field is required.'),
      },
      url: {
        required: validations.required('This field is required.'),
      },
      type: {
        required: validations.required('This field is required.'),
      },
      auth: {
        required: validations.required('This field is required.'),
      },
      description: {},
      version: {
        maxLength: validations.maxLength('This field cannot be longer than 50 characters.', 50),
      },
      timeout: {},
      retryRetries: {},
      retryDelay: {},
    };
    const v$ = useVuelidate(validationRules, apiIntegration as any);
    v$.value.$validate();

    return {
      apiIntegrationService,
      alertService,
      apiIntegration,
      previousState,
      contentTypeValues,
      authTypeValues,
      isSaving,
      currentLanguage,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.apiIntegration.id) {
        this.apiIntegrationService()
          .update(this.apiIntegration)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A ApiIntegration is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.apiIntegrationService()
          .create(this.apiIntegration)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A ApiIntegration is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
