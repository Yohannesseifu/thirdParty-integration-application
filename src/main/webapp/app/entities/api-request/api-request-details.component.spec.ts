/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApiRequestDetails from './api-request-details.vue';
import ApiRequestService from './api-request.service';
import AlertService from '@/shared/alert/alert.service';

type ApiRequestDetailsComponentType = InstanceType<typeof ApiRequestDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const apiRequestSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('ApiRequest Management Detail Component', () => {
    let apiRequestServiceStub: SinonStubbedInstance<ApiRequestService>;
    let mountOptions: MountingOptions<ApiRequestDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      apiRequestServiceStub = sinon.createStubInstance<ApiRequestService>(ApiRequestService);

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
          apiRequestService: () => apiRequestServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        apiRequestServiceStub.find.resolves(apiRequestSample);
        route = {
          params: {
            apiRequestId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(ApiRequestDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.apiRequest).toMatchObject(apiRequestSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        apiRequestServiceStub.find.resolves(apiRequestSample);
        const wrapper = shallowMount(ApiRequestDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
