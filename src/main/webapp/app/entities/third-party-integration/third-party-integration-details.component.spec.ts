/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ThirdPartyIntegrationDetails from './third-party-integration-details.vue';
import ThirdPartyIntegrationService from './third-party-integration.service';
import AlertService from '@/shared/alert/alert.service';

type ThirdPartyIntegrationDetailsComponentType = InstanceType<typeof ThirdPartyIntegrationDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const thirdPartyIntegrationSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ThirdPartyIntegration Management Detail Component', () => {
    let thirdPartyIntegrationServiceStub: SinonStubbedInstance<ThirdPartyIntegrationService>;
    let mountOptions: MountingOptions<ThirdPartyIntegrationDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      thirdPartyIntegrationServiceStub = sinon.createStubInstance<ThirdPartyIntegrationService>(ThirdPartyIntegrationService);

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
          thirdPartyIntegrationService: () => thirdPartyIntegrationServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        thirdPartyIntegrationServiceStub.find.resolves(thirdPartyIntegrationSample);
        route = {
          params: {
            thirdPartyIntegrationId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(ThirdPartyIntegrationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.thirdPartyIntegration).toMatchObject(thirdPartyIntegrationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        thirdPartyIntegrationServiceStub.find.resolves(thirdPartyIntegrationSample);
        const wrapper = shallowMount(ThirdPartyIntegrationDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
