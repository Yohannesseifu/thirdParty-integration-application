import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import IntegrationFieldMappingService from './integration-field-mapping.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import IntegrationOperationService from '@/entities/integration-operation/integration-operation.service';
import { type IIntegrationOperation } from '@/shared/model/integration-operation.model';
import FieldService from '@/entities/field/field.service';
import { type IField } from '@/shared/model/field.model';
import RequestInputService from '@/entities/request-input/request-input.service';
import { type IRequestInput } from '@/shared/model/request-input.model';
import { type IIntegrationFieldMapping, IntegrationFieldMapping } from '@/shared/model/integration-field-mapping.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'IntegrationFieldMappingUpdate',
  setup() {
    const integrationFieldMappingService = inject('integrationFieldMappingService', () => new IntegrationFieldMappingService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const integrationFieldMapping: Ref<IIntegrationFieldMapping> = ref(new IntegrationFieldMapping());

    const integrationOperationService = inject('integrationOperationService', () => new IntegrationOperationService());

    const integrationOperations: Ref<IIntegrationOperation[]> = ref([]);

    const fieldService = inject('fieldService', () => new FieldService());

    const fields: Ref<IField[]> = ref([]);

    const requestInputService = inject('requestInputService', () => new RequestInputService());

    const requestInputs: Ref<IRequestInput[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveIntegrationFieldMapping = async integrationFieldMappingId => {
      try {
        const res = await integrationFieldMappingService().find(integrationFieldMappingId);
        integrationFieldMapping.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.integrationFieldMappingId) {
      retrieveIntegrationFieldMapping(route.params.integrationFieldMappingId);
    }

    const initRelationships = () => {
      integrationOperationService()
        .retrieve()
        .then(res => {
          integrationOperations.value = res.data;
        });
      fieldService()
        .retrieve()
        .then(res => {
          fields.value = res.data;
        });
      requestInputService()
        .retrieve()
        .then(res => {
          requestInputs.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      integrationOperation: {},
      field: {},
      requestInput: {},
    };
    const v$ = useVuelidate(validationRules, integrationFieldMapping as any);
    v$.value.$validate();

    return {
      integrationFieldMappingService,
      alertService,
      integrationFieldMapping,
      previousState,
      isSaving,
      currentLanguage,
      integrationOperations,
      fields,
      requestInputs,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.integrationFieldMapping.id) {
        this.integrationFieldMappingService()
          .update(this.integrationFieldMapping)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A IntegrationFieldMapping is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.integrationFieldMappingService()
          .create(this.integrationFieldMapping)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A IntegrationFieldMapping is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
