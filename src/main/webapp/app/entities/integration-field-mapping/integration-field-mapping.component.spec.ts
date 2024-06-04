/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import IntegrationFieldMapping from './integration-field-mapping.vue';
import IntegrationFieldMappingService from './integration-field-mapping.service';
import AlertService from '@/shared/alert/alert.service';

type IntegrationFieldMappingComponentType = InstanceType<typeof IntegrationFieldMapping>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('IntegrationFieldMapping Management Component', () => {
    let integrationFieldMappingServiceStub: SinonStubbedInstance<IntegrationFieldMappingService>;
    let mountOptions: MountingOptions<IntegrationFieldMappingComponentType>['global'];

    beforeEach(() => {
      integrationFieldMappingServiceStub = sinon.createStubInstance<IntegrationFieldMappingService>(IntegrationFieldMappingService);
      integrationFieldMappingServiceStub.retrieve.resolves({ headers: {} });

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
          integrationFieldMappingService: () => integrationFieldMappingServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        integrationFieldMappingServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(IntegrationFieldMapping, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(integrationFieldMappingServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.integrationFieldMappings[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: IntegrationFieldMappingComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(IntegrationFieldMapping, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        integrationFieldMappingServiceStub.retrieve.reset();
        integrationFieldMappingServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        integrationFieldMappingServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeIntegrationFieldMapping();
        await comp.$nextTick(); // clear components

        // THEN
        expect(integrationFieldMappingServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(integrationFieldMappingServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
