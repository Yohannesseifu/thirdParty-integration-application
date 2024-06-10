import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ApiRequestService from './api-request.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IApiRequest, ApiRequest } from '@/shared/model/api-request.model';
import { HttpMethod } from '@/shared/model/enumerations/http-method.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApiRequestUpdate',
  setup() {
    const apiRequestService = inject('apiRequestService', () => new ApiRequestService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const apiRequest: Ref<IApiRequest> = ref(new ApiRequest());
    const httpMethodValues: Ref<string[]> = ref(Object.keys(HttpMethod));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveApiRequest = async apiRequestId => {
      try {
        const res = await apiRequestService().find(apiRequestId);
        apiRequest.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.apiRequestId) {
      retrieveApiRequest(route.params.apiRequestId);
    }

    const validations = useValidation();
    const validationRules = {
      uri: {
        required: validations.required('This field is required.'),
      },
      body: {},
      method: {
        required: validations.required('This field is required.'),
      },
    };
    const v$ = useVuelidate(validationRules, apiRequest as any);
    v$.value.$validate();

    return {
      apiRequestService,
      alertService,
      apiRequest,
      previousState,
      httpMethodValues,
      isSaving,
      currentLanguage,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.apiRequest.id) {
        this.apiRequestService()
          .update(this.apiRequest)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A ApiRequest is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.apiRequestService()
          .create(this.apiRequest)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A ApiRequest is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
