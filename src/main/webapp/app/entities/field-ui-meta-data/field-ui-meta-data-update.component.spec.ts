/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FieldUIMetaDataUpdate from './field-ui-meta-data-update.vue';
import FieldUIMetaDataService from './field-ui-meta-data.service';
import AlertService from '@/shared/alert/alert.service';

type FieldUIMetaDataUpdateComponentType = InstanceType<typeof FieldUIMetaDataUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fieldUIMetaDataSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FieldUIMetaDataUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('FieldUIMetaData Management Update Component', () => {
    let comp: FieldUIMetaDataUpdateComponentType;
    let fieldUIMetaDataServiceStub: SinonStubbedInstance<FieldUIMetaDataService>;

    beforeEach(() => {
      route = {};
      fieldUIMetaDataServiceStub = sinon.createStubInstance<FieldUIMetaDataService>(FieldUIMetaDataService);
      fieldUIMetaDataServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          fieldUIMetaDataService: () => fieldUIMetaDataServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(FieldUIMetaDataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fieldUIMetaData = fieldUIMetaDataSample;
        fieldUIMetaDataServiceStub.update.resolves(fieldUIMetaDataSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fieldUIMetaDataServiceStub.update.calledWith(fieldUIMetaDataSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        fieldUIMetaDataServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FieldUIMetaDataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.fieldUIMetaData = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fieldUIMetaDataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        fieldUIMetaDataServiceStub.find.resolves(fieldUIMetaDataSample);
        fieldUIMetaDataServiceStub.retrieve.resolves([fieldUIMetaDataSample]);

        // WHEN
        route = {
          params: {
            fieldUIMetaDataId: '' + fieldUIMetaDataSample.id,
          },
        };
        const wrapper = shallowMount(FieldUIMetaDataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.fieldUIMetaData).toMatchObject(fieldUIMetaDataSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fieldUIMetaDataServiceStub.find.resolves(fieldUIMetaDataSample);
        const wrapper = shallowMount(FieldUIMetaDataUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
