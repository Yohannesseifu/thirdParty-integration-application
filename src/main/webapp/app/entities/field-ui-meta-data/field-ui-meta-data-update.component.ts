import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FieldUIMetaDataService from './field-ui-meta-data.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IFieldUIMetaData, FieldUIMetaData } from '@/shared/model/field-ui-meta-data.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FieldUIMetaDataUpdate',
  setup() {
    const fieldUIMetaDataService = inject('fieldUIMetaDataService', () => new FieldUIMetaDataService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const fieldUIMetaData: Ref<IFieldUIMetaData> = ref(new FieldUIMetaData());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveFieldUIMetaData = async fieldUIMetaDataId => {
      try {
        const res = await fieldUIMetaDataService().find(fieldUIMetaDataId);
        fieldUIMetaData.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fieldUIMetaDataId) {
      retrieveFieldUIMetaData(route.params.fieldUIMetaDataId);
    }

    const validations = useValidation();
    const validationRules = {
      label: {},
      inputInterface: {},
      width: {},
      note: {},
      validationPattern: {},
      options: {},
      displayOptions: {},
      conditions: {},
      translations: {},
    };
    const v$ = useVuelidate(validationRules, fieldUIMetaData as any);
    v$.value.$validate();

    return {
      fieldUIMetaDataService,
      alertService,
      fieldUIMetaData,
      previousState,
      isSaving,
      currentLanguage,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.fieldUIMetaData.id) {
        this.fieldUIMetaDataService()
          .update(this.fieldUIMetaData)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A FieldUIMetaData is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.fieldUIMetaDataService()
          .create(this.fieldUIMetaData)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A FieldUIMetaData is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
