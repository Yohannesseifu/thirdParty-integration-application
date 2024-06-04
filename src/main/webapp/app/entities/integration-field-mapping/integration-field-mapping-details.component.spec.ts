/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import IntegrationFieldMappingDetails from './integration-field-mapping-details.vue';
import IntegrationFieldMappingService from './integration-field-mapping.service';
import AlertService from '@/shared/alert/alert.service';

type IntegrationFieldMappingDetailsComponentType = InstanceType<typeof IntegrationFieldMappingDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const integrationFieldMappingSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('IntegrationFieldMapping Management Detail Component', () => {
    let integrationFieldMappingServiceStub: SinonStubbedInstance<IntegrationFieldMappingService>;
    let mountOptions: MountingOptions<IntegrationFieldMappingDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      integrationFieldMappingServiceStub = sinon.createStubInstance<IntegrationFieldMappingService>(IntegrationFieldMappingService);

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
          integrationFieldMappingService: () => integrationFieldMappingServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        integrationFieldMappingServiceStub.find.resolves(integrationFieldMappingSample);
        route = {
          params: {
            integrationFieldMappingId: '' + 123,
          },
        };
        const wrapper = shallowMount(IntegrationFieldMappingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.integrationFieldMapping).toMatchObject(integrationFieldMappingSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        integrationFieldMappingServiceStub.find.resolves(integrationFieldMappingSample);
        const wrapper = shallowMount(IntegrationFieldMappingDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
