/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentParamDetails from './payment-param-details.vue';
import PaymentParamService from './payment-param.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentParamDetailsComponentType = InstanceType<typeof PaymentParamDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentParamSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PaymentParam Management Detail Component', () => {
    let paymentParamServiceStub: SinonStubbedInstance<PaymentParamService>;
    let mountOptions: MountingOptions<PaymentParamDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      paymentParamServiceStub = sinon.createStubInstance<PaymentParamService>(PaymentParamService);

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
          paymentParamService: () => paymentParamServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentParamServiceStub.find.resolves(paymentParamSample);
        route = {
          params: {
            paymentParamId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(PaymentParamDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.paymentParam).toMatchObject(paymentParamSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentParamServiceStub.find.resolves(paymentParamSample);
        const wrapper = shallowMount(PaymentParamDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
