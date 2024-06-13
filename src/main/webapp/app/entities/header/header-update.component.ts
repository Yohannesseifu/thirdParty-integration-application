import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import HeaderService from './header.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ApiRequestService from '@/entities/api-request/api-request.service';
import { type IApiRequest } from '@/shared/model/api-request.model';
import { type IHeader, Header } from '@/shared/model/header.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'HeaderUpdate',
  setup() {
    const headerService = inject('headerService', () => new HeaderService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const header: Ref<IHeader> = ref(new Header());

    const apiRequestService = inject('apiRequestService', () => new ApiRequestService());

    const apiRequests: Ref<IApiRequest[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      apiRequestService()
        .retrieve()
        .then(res => {
          apiRequests.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      name: {
        required: validations.required('This field is required.'),
      },
      valueStr: {
        required: validations.required('This field is required.'),
      },
      apiRequest: {},
    };
    const v$ = useVuelidate(validationRules, header as any);
    v$.value.$validate();

    return {
      headerService,
      alertService,
      header,
      previousState,
      isSaving,
      currentLanguage,
      apiRequests,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.header.id) {
        this.headerService()
          .update(this.header)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Header is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.headerService()
          .create(this.header)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Header is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
