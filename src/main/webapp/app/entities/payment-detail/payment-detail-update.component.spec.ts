/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PaymentDetailUpdate from './payment-detail-update.vue';
import PaymentDetailService from './payment-detail.service';
import AlertService from '@/shared/alert/alert.service';

import ApiRequestService from '@/entities/api-request/api-request.service';
import OperationService from '@/entities/operation/operation.service';

type PaymentDetailUpdateComponentType = InstanceType<typeof PaymentDetailUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const paymentDetailSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PaymentDetailUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('PaymentDetail Management Update Component', () => {
    let comp: PaymentDetailUpdateComponentType;
    let paymentDetailServiceStub: SinonStubbedInstance<PaymentDetailService>;

    beforeEach(() => {
      route = {};
      paymentDetailServiceStub = sinon.createStubInstance<PaymentDetailService>(PaymentDetailService);
      paymentDetailServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          paymentDetailService: () => paymentDetailServiceStub,
          apiRequestService: () =>
            sinon.createStubInstance<ApiRequestService>(ApiRequestService, {
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
        const wrapper = shallowMount(PaymentDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentDetail = paymentDetailSample;
        paymentDetailServiceStub.update.resolves(paymentDetailSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentDetailServiceStub.update.calledWith(paymentDetailSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        paymentDetailServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PaymentDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.paymentDetail = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(paymentDetailServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        paymentDetailServiceStub.find.resolves(paymentDetailSample);
        paymentDetailServiceStub.retrieve.resolves([paymentDetailSample]);

        // WHEN
        route = {
          params: {
            paymentDetailId: '' + paymentDetailSample.id,
          },
        };
        const wrapper = shallowMount(PaymentDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.paymentDetail).toMatchObject(paymentDetailSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        paymentDetailServiceStub.find.resolves(paymentDetailSample);
        const wrapper = shallowMount(PaymentDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
