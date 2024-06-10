/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApiRequestUpdate from './api-request-update.vue';
import ApiRequestService from './api-request.service';
import AlertService from '@/shared/alert/alert.service';

type ApiRequestUpdateComponentType = InstanceType<typeof ApiRequestUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const apiRequestSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ApiRequestUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ApiRequest Management Update Component', () => {
    let comp: ApiRequestUpdateComponentType;
    let apiRequestServiceStub: SinonStubbedInstance<ApiRequestService>;

    beforeEach(() => {
      route = {};
      apiRequestServiceStub = sinon.createStubInstance<ApiRequestService>(ApiRequestService);
      apiRequestServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          apiRequestService: () => apiRequestServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ApiRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.apiRequest = apiRequestSample;
        apiRequestServiceStub.update.resolves(apiRequestSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiRequestServiceStub.update.calledWith(apiRequestSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        apiRequestServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ApiRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.apiRequest = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apiRequestServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        apiRequestServiceStub.find.resolves(apiRequestSample);
        apiRequestServiceStub.retrieve.resolves([apiRequestSample]);

        // WHEN
        route = {
          params: {
            apiRequestId: '' + apiRequestSample.id,
          },
        };
        const wrapper = shallowMount(ApiRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.apiRequest).toMatchObject(apiRequestSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        apiRequestServiceStub.find.resolves(apiRequestSample);
        const wrapper = shallowMount(ApiRequestUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
