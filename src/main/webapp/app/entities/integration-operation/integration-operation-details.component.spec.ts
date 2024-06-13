/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntegrationOperationDetails from './integration-operation-details.vue';
import IntegrationOperationService from './integration-operation.service';
import AlertService from '@/shared/alert/alert.service';

type IntegrationOperationDetailsComponentType = InstanceType<typeof IntegrationOperationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const integrationOperationSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('IntegrationOperation Management Detail Component', () => {
    let integrationOperationServiceStub: SinonStubbedInstance<IntegrationOperationService>;
    let mountOptions: MountingOptions<IntegrationOperationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      integrationOperationServiceStub = sinon.createStubInstance<IntegrationOperationService>(IntegrationOperationService);

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          integrationOperationService: () => integrationOperationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        integrationOperationServiceStub.find.resolves(integrationOperationSample);
        route = {
          params: {
            integrationOperationId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(IntegrationOperationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.integrationOperation).toMatchObject(integrationOperationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        integrationOperationServiceStub.find.resolves(integrationOperationSample);
        const wrapper = shallowMount(IntegrationOperationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
