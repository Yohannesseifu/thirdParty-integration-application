/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RequestInputDetails from './request-input-details.vue';
import RequestInputService from './request-input.service';
import AlertService from '@/shared/alert/alert.service';

type RequestInputDetailsComponentType = InstanceType<typeof RequestInputDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const requestInputSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('RequestInput Management Detail Component', () => {
    let requestInputServiceStub: SinonStubbedInstance<RequestInputService>;
    let mountOptions: MountingOptions<RequestInputDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      requestInputServiceStub = sinon.createStubInstance<RequestInputService>(RequestInputService);

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
          requestInputService: () => requestInputServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        requestInputServiceStub.find.resolves(requestInputSample);
        route = {
          params: {
            requestInputId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(RequestInputDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.requestInput).toMatchObject(requestInputSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        requestInputServiceStub.find.resolves(requestInputSample);
        const wrapper = shallowMount(RequestInputDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
