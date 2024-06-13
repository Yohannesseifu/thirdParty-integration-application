/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import OperationUpdate from './operation-update.vue';
import OperationService from './operation.service';
import AlertService from '@/shared/alert/alert.service';

import ApiIntegrationService from '@/entities/api-integration/api-integration.service';

type OperationUpdateComponentType = InstanceType<typeof OperationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const operationSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<OperationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Operation Management Update Component', () => {
    let comp: OperationUpdateComponentType;
    let operationServiceStub: SinonStubbedInstance<OperationService>;

    beforeEach(() => {
      route = {};
      operationServiceStub = sinon.createStubInstance<OperationService>(OperationService);
      operationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          operationService: () => operationServiceStub,
          apiIntegrationService: () =>
            sinon.createStubInstance<ApiIntegrationService>(ApiIntegrationService, {
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
        const wrapper = shallowMount(OperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.operation = operationSample;
        operationServiceStub.update.resolves(operationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operationServiceStub.update.calledWith(operationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        operationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(OperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.operation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(operationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        operationServiceStub.find.resolves(operationSample);
        operationServiceStub.retrieve.resolves([operationSample]);

        // WHEN
        route = {
          params: {
            operationId: '' + operationSample.id,
          },
        };
        const wrapper = shallowMount(OperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.operation).toMatchObject(operationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        operationServiceStub.find.resolves(operationSample);
        const wrapper = shallowMount(OperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
