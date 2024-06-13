/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentDetailDetails from './payment-detail-details.vue';
import PaymentDetailService from './payment-detail.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentDetailDetailsComponentType = InstanceType<typeof PaymentDetailDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentDetailSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('PaymentDetail Management Detail Component', () => {
    let paymentDetailServiceStub: SinonStubbedInstance<PaymentDetailService>;
    let mountOptions: MountingOptions<PaymentDetailDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      paymentDetailServiceStub = sinon.createStubInstance<PaymentDetailService>(PaymentDetailService);

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
          paymentDetailService: () => paymentDetailServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentDetailServiceStub.find.resolves(paymentDetailSample);
        route = {
          params: {
            paymentDetailId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(PaymentDetailDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.paymentDetail).toMatchObject(paymentDetailSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentDetailServiceStub.find.resolves(paymentDetailSample);
        const wrapper = shallowMount(PaymentDetailDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
