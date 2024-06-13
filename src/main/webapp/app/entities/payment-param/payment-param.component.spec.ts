/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import PaymentParam from './payment-param.vue';
import PaymentParamService from './payment-param.service';
import AlertService from '@/shared/alert/alert.service';

type PaymentParamComponentType = InstanceType<typeof PaymentParam>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('PaymentParam Management Component', () => {
    let paymentParamServiceStub: SinonStubbedInstance<PaymentParamService>;
    let mountOptions: MountingOptions<PaymentParamComponentType>['global'];

    beforeEach(() => {
      paymentParamServiceStub = sinon.createStubInstance<PaymentParamService>(PaymentParamService);
      paymentParamServiceStub.retrieve.resolves({ headers: {} });

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
          paymentParamService: () => paymentParamServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        paymentParamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(PaymentParam, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(paymentParamServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.paymentParams[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: PaymentParamComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(PaymentParam, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        paymentParamServiceStub.retrieve.reset();
        paymentParamServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        paymentParamServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removePaymentParam();
        await comp.$nextTick(); // clear components

        // THEN
        expect(paymentParamServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(paymentParamServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
