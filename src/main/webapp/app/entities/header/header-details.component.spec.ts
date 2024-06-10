/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HeaderDetails from './header-details.vue';
import HeaderService from './header.service';
import AlertService from '@/shared/alert/alert.service';

type HeaderDetailsComponentType = InstanceType<typeof HeaderDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const headerSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Header Management Detail Component', () => {
    let headerServiceStub: SinonStubbedInstance<HeaderService>;
    let mountOptions: MountingOptions<HeaderDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      headerServiceStub = sinon.createStubInstance<HeaderService>(HeaderService);

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
          headerService: () => headerServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        headerServiceStub.find.resolves(headerSample);
        route = {
          params: {
            headerId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(HeaderDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.header).toMatchObject(headerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        headerServiceStub.find.resolves(headerSample);
        const wrapper = shallowMount(HeaderDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
