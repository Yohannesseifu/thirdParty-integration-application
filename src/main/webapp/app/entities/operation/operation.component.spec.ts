/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Operation from './operation.vue';
import OperationService from './operation.service';
import AlertService from '@/shared/alert/alert.service';

type OperationComponentType = InstanceType<typeof Operation>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Operation Management Component', () => {
    let operationServiceStub: SinonStubbedInstance<OperationService>;
    let mountOptions: MountingOptions<OperationComponentType>['global'];

    beforeEach(() => {
      operationServiceStub = sinon.createStubInstance<OperationService>(OperationService);
      operationServiceStub.retrieve.resolves({ headers: {} });

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
          operationService: () => operationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        operationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(Operation, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(operationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.operations[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: OperationComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Operation, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        operationServiceStub.retrieve.reset();
        operationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        operationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removeOperation();
        await comp.$nextTick(); // clear components

        // THEN
        expect(operationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(operationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
