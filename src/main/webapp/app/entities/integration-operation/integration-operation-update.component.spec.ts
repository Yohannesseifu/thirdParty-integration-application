/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntegrationOperationUpdate from './integration-operation-update.vue';
import IntegrationOperationService from './integration-operation.service';
import AlertService from '@/shared/alert/alert.service';

import ThirdPartyIntegrationService from '@/entities/third-party-integration/third-party-integration.service';
import OperationService from '@/entities/operation/operation.service';

type IntegrationOperationUpdateComponentType = InstanceType<typeof IntegrationOperationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const integrationOperationSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<IntegrationOperationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('IntegrationOperation Management Update Component', () => {
    let comp: IntegrationOperationUpdateComponentType;
    let integrationOperationServiceStub: SinonStubbedInstance<IntegrationOperationService>;

    beforeEach(() => {
      route = {};
      integrationOperationServiceStub = sinon.createStubInstance<IntegrationOperationService>(IntegrationOperationService);
      integrationOperationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          integrationOperationService: () => integrationOperationServiceStub,
          thirdPartyIntegrationService: () =>
            sinon.createStubInstance<ThirdPartyIntegrationService>(ThirdPartyIntegrationService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          operationService: () =>
            sinon.createStubInstance<OperationService>(OperationService, {
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
        const wrapper = shallowMount(IntegrationOperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.integrationOperation = integrationOperationSample;
        integrationOperationServiceStub.update.resolves(integrationOperationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(integrationOperationServiceStub.update.calledWith(integrationOperationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        integrationOperationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(IntegrationOperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.integrationOperation = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(integrationOperationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        integrationOperationServiceStub.find.resolves(integrationOperationSample);
        integrationOperationServiceStub.retrieve.resolves([integrationOperationSample]);

        // WHEN
        route = {
          params: {
            integrationOperationId: '' + integrationOperationSample.id,
          },
        };
        const wrapper = shallowMount(IntegrationOperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.integrationOperation).toMatchObject(integrationOperationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        integrationOperationServiceStub.find.resolves(integrationOperationSample);
        const wrapper = shallowMount(IntegrationOperationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
