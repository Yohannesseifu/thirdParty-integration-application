/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PaymentDetail from './payment-detail.vue';
import PaymentDetailService from './payment-detail.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentDetailComponentType = InstanceType<typeof PaymentDetail>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PaymentDetail Management Component', () => {
    let paymentDetailServiceStub: SinonStubbedInstance<PaymentDetailService>;
    let mountOptions: MountingOptions<PaymentDetailComponentType>['global'];

    beforeEach(() => {
      paymentDetailServiceStub = sinon.createStubInstance<PaymentDetailService>(PaymentDetailService);
      paymentDetailServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          paymentDetailService: () => paymentDetailServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentDetailServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(PaymentDetail, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(paymentDetailServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.paymentDetails[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: PaymentDetailComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PaymentDetail, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        paymentDetailServiceStub.retrieve.reset();
        paymentDetailServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        paymentDetailServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removePaymentDetail();
        await comp.$nextTick(); // clear components

        // THEN
        expect(paymentDetailServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(paymentDetailServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});