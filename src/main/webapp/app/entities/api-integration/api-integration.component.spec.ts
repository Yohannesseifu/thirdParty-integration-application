/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ApiIntegration from './api-integration.vue';
import ApiIntegrationService from './api-integration.service';
import AlertService from '@/shared/alert/alert.service';

type ApiIntegrationComponentType = InstanceType<typeof ApiIntegration>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ApiIntegration Management Component', () => {
    let apiIntegrationServiceStub: SinonStubbedInstance<ApiIntegrationService>;
    let mountOptions: MountingOptions<ApiIntegrationComponentType>['global'];

    beforeEach(() => {
      apiIntegrationServiceStub = sinon.createStubInstance<ApiIntegrationService>(ApiIntegrationService);
      apiIntegrationServiceStub.retrieve.resolves({ headers: {} });

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
          apiIntegrationService: () => apiIntegrationServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        apiIntegrationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ApiIntegration, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(apiIntegrationServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.apiIntegrations[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ApiIntegrationComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ApiIntegration, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        apiIntegrationServiceStub.retrieve.reset();
        apiIntegrationServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        apiIntegrationServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeApiIntegration();
        await comp.$nextTick(); // clear components

        // THEN
        expect(apiIntegrationServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(apiIntegrationServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
