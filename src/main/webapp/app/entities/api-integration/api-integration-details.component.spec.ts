/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApiIntegrationDetails from './api-integration-details.vue';
import ApiIntegrationService from './api-integration.service';
import AlertService from '@/shared/alert/alert.service';

type ApiIntegrationDetailsComponentType = InstanceType<typeof ApiIntegrationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const apiIntegrationSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ApiIntegration Management Detail Component', () => {
    let apiIntegrationServiceStub: SinonStubbedInstance<ApiIntegrationService>;
    let mountOptions: MountingOptions<ApiIntegrationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      apiIntegrationServiceStub = sinon.createStubInstance<ApiIntegrationService>(ApiIntegrationService);

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
          apiIntegrationService: () => apiIntegrationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        apiIntegrationServiceStub.find.resolves(apiIntegrationSample);
        route = {
          params: {
            apiIntegrationId: '' + 123,
          },
        };
        const wrapper = shallowMount(ApiIntegrationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.apiIntegration).toMatchObject(apiIntegrationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        apiIntegrationServiceStub.find.resolves(apiIntegrationSample);
        const wrapper = shallowMount(ApiIntegrationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
