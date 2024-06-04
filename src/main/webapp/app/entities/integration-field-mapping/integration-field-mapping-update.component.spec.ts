/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntegrationFieldMappingUpdate from './integration-field-mapping-update.vue';
import IntegrationFieldMappingService from './integration-field-mapping.service';
import AlertService from '@/shared/alert/alert.service';

import IntegrationOperationService from '@/entities/integration-operation/integration-operation.service';
import FieldService from '@/entities/field/field.service';
import RequestInputService from '@/entities/request-input/request-input.service';

type IntegrationFieldMappingUpdateComponentType = InstanceType<typeof IntegrationFieldMappingUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const integrationFieldMappingSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<IntegrationFieldMappingUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('IntegrationFieldMapping Management Update Component', () => {
    let comp: IntegrationFieldMappingUpdateComponentType;
    let integrationFieldMappingServiceStub: SinonStubbedInstance<IntegrationFieldMappingService>;

    beforeEach(() => {
      route = {};
      integrationFieldMappingServiceStub = sinon.createStubInstance<IntegrationFieldMappingService>(IntegrationFieldMappingService);
      integrationFieldMappingServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          integrationFieldMappingService: () => integrationFieldMappingServiceStub,
          integrationOperationService: () =>
            sinon.createStubInstance<IntegrationOperationService>(IntegrationOperationService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          fieldService: () =>
            sinon.createStubInstance<FieldService>(FieldService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          requestInputService: () =>
            sinon.createStubInstance<RequestInputService>(RequestInputService, {
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
        const wrapper = shallowMount(IntegrationFieldMappingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.integrationFieldMapping = integrationFieldMappingSample;
        integrationFieldMappingServiceStub.update.resolves(integrationFieldMappingSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(integrationFieldMappingServiceStub.update.calledWith(integrationFieldMappingSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        integrationFieldMappingServiceStub.create.resolves(entity);
        const wrapper = shallowMount(IntegrationFieldMappingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.integrationFieldMapping = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(integrationFieldMappingServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        integrationFieldMappingServiceStub.find.resolves(integrationFieldMappingSample);
        integrationFieldMappingServiceStub.retrieve.resolves([integrationFieldMappingSample]);

        // WHEN
        route = {
          params: {
            integrationFieldMappingId: '' + integrationFieldMappingSample.id,
          },
        };
        const wrapper = shallowMount(IntegrationFieldMappingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.integrationFieldMapping).toMatchObject(integrationFieldMappingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        integrationFieldMappingServiceStub.find.resolves(integrationFieldMappingSample);
        const wrapper = shallowMount(IntegrationFieldMappingUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
