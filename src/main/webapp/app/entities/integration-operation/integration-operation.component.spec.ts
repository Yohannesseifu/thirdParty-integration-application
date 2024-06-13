/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import IntegrationOperation from './integration-operation.vue';
import IntegrationOperationService from './integration-operation.service';
import AlertService from '@/shared/alert/alert.service';

type IntegrationOperationComponentType = InstanceType<typeof IntegrationOperation>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('IntegrationOperation Management Component', () => {
    let integrationOperationServiceStub: SinonStubbedInstance<IntegrationOperationService>;
    let mountOptions: MountingOptions<IntegrationOperationComponentType>['global'];

    beforeEach(() => {
      integrationOperationServiceStub = sinon.createStubInstance<IntegrationOperationService>(IntegrationOperationService);
      integrationOperationServiceStub.retrieve.resolves({ headers: {} });

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
          integrationOperationService: () => integrationOperationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        integrationOperationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(IntegrationOperation, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(integrationOperationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.integrationOperations[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: IntegrationOperationComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(IntegrationOperation, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        integrationOperationServiceStub.retrieve.reset();
        integrationOperationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        integrationOperationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removeIntegrationOperation();
        await comp.$nextTick(); // clear components

        // THEN
        expect(integrationOperationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(integrationOperationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
