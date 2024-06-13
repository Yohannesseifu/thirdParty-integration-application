/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApiIntegrationUpdate from './api-integration-update.vue';
import ApiIntegrationService from './api-integration.service';
import AlertService from '@/shared/alert/alert.service';

type ApiIntegrationUpdateComponentType = InstanceType<typeof ApiIntegrationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const apiIntegrationSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ApiIntegrationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ApiIntegration Management Update Component', () => {
    let comp: ApiIntegrationUpdateComponentType;
    let apiIntegrationServiceStub: SinonStubbedInstance<ApiIntegrationService>;

    beforeEach(() => {
      route = {};
      apiIntegrationServiceStub = sinon.createStubInstance<ApiIntegrationService>(ApiIntegrationService);
      apiIntegrationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          apiIntegrationService: () => apiIntegrationServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ApiIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.apiIntegration = apiIntegrationSample;
        apiIntegrationServiceStub.update.resolves(apiIntegrationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiIntegrationServiceStub.update.calledWith(apiIntegrationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        apiIntegrationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ApiIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.apiIntegration = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiIntegrationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        apiIntegrationServiceStub.find.resolves(apiIntegrationSample);
        apiIntegrationServiceStub.retrieve.resolves([apiIntegrationSample]);

        // WHEN
        route = {
          params: {
            apiIntegrationId: '' + apiIntegrationSample.id,
          },
        };
        const wrapper = shallowMount(ApiIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.apiIntegration).toMatchObject(apiIntegrationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        apiIntegrationServiceStub.find.resolves(apiIntegrationSample);
        const wrapper = shallowMount(ApiIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
