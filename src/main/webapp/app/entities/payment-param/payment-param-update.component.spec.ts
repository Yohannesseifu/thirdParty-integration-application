/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentParamUpdate from './payment-param-update.vue';
import PaymentParamService from './payment-param.service';
import AlertService from '@/shared/alert/alert.service';

import PaymentDetailService from '@/entities/payment-detail/payment-detail.service';

type PaymentParamUpdateComponentType = InstanceType<typeof PaymentParamUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentParamSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PaymentParamUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PaymentParam Management Update Component', () => {
    let comp: PaymentParamUpdateComponentType;
    let paymentParamServiceStub: SinonStubbedInstance<PaymentParamService>;

    beforeEach(() => {
      route = {};
      paymentParamServiceStub = sinon.createStubInstance<PaymentParamService>(PaymentParamService);
      paymentParamServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          paymentParamService: () => paymentParamServiceStub,
          paymentDetailService: () =>
            sinon.createStubInstance<PaymentDetailService>(PaymentDetailService, {
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
        const wrapper = shallowMount(PaymentParamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentParam = paymentParamSample;
        paymentParamServiceStub.update.resolves(paymentParamSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentParamServiceStub.update.calledWith(paymentParamSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        paymentParamServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PaymentParamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentParam = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentParamServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        paymentParamServiceStub.find.resolves(paymentParamSample);
        paymentParamServiceStub.retrieve.resolves([paymentParamSample]);

        // WHEN
        route = {
          params: {
            paymentParamId: '' + paymentParamSample.id,
          },
        };
        const wrapper = shallowMount(PaymentParamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.paymentParam).toMatchObject(paymentParamSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentParamServiceStub.find.resolves(paymentParamSample);
        const wrapper = shallowMount(PaymentParamUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
