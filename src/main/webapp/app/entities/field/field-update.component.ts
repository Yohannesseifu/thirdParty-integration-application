import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import FieldService from './field.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import FormUiService from '@/entities/form-ui/form-ui.service';
import { type IFormUi } from '@/shared/model/form-ui.model';
import FieldUIMetaDataService from '@/entities/field-ui-meta-data/field-ui-meta-data.service';
import { type IFieldUIMetaData } from '@/shared/model/field-ui-meta-data.model';
import { type IField, Field } from '@/shared/model/field.model';
import { DataType } from '@/shared/model/enumerations/data-type.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'FieldUpdate',
  setup() {
    const fieldService = inject('fieldService', () => new FieldService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const field: Ref<IField> = ref(new Field());

    const formUiService = inject('formUiService', () => new FormUiService());

    const formUis: Ref<IFormUi[]> = ref([]);

    const fieldUIMetaDataService = inject('fieldUIMetaDataService', () => new FieldUIMetaDataService());

    const fieldUIMetaData: Ref<IFieldUIMetaData[]> = ref([]);
    const dataTypeValues: Ref<string[]> = ref(Object.keys(DataType));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveField = async fieldId => {
      try {
        const res = await fieldService().find(fieldId);
        field.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.fieldId) {
      retrieveField(route.params.fieldId);
    }

    const initRelationships = () => {
      formUiService()
        .retrieve()
        .then(res => {
          formUis.value = res.data;
        });
      fieldUIMetaDataService()
        .retrieve()
        .then(res => {
          fieldUIMetaData.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      name: {},
      dataType: {},
      isUnique: {},
      maxLength: {},
      minLength: {},
      minValue: {},
      maxValue: {},
      isRequired: {},
      sortOrder: {},
      formUi: {},
      fieldUIMetaData: {},
    };
    const v$ = useVuelidate(validationRules, field as any);
    v$.value.$validate();

    return {
      fieldService,
      alertService,
      field,
      previousState,
      dataTypeValues,
      isSaving,
      currentLanguage,
      formUis,
      fieldUIMetaData,
      v$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.field.id) {
        this.fieldService()
          .update(this.field)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Field is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.fieldService()
          .create(this.field)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Field is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
