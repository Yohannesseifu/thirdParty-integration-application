/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FieldUpdate from './field-update.vue';
import FieldService from './field.service';
import AlertService from '@/shared/alert/alert.service';

import FormUiService from '@/entities/form-ui/form-ui.service';
import FieldUIMetaDataService from '@/entities/field-ui-meta-data/field-ui-meta-data.service';

type FieldUpdateComponentType = InstanceType<typeof FieldUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fieldSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FieldUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Field Management Update Component', () => {
    let comp: FieldUpdateComponentType;
    let fieldServiceStub: SinonStubbedInstance<FieldService>;

    beforeEach(() => {
      route = {};
      fieldServiceStub = sinon.createStubInstance<FieldService>(FieldService);
      fieldServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          fieldService: () => fieldServiceStub,
          formUiService: () =>
            sinon.createStubInstance<FormUiService>(FormUiService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          fieldUIMetaDataService: () =>
            sinon.createStubInstance<FieldUIMetaDataService>(FieldUIMetaDataService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(FieldUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.field = fieldSample;
        fieldServiceStub.update.resolves(fieldSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fieldServiceStub.update.calledWith(fieldSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        fieldServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FieldUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.field = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fieldServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        fieldServiceStub.find.resolves(fieldSample);
        fieldServiceStub.retrieve.resolves([fieldSample]);

        // WHEN
        route = {
          params: {
            fieldId: '' + fieldSample.id,
          },
        };
        const wrapper = shallowMount(FieldUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.field).toMatchObject(fieldSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fieldServiceStub.find.resolves(fieldSample);
        const wrapper = shallowMount(FieldUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
