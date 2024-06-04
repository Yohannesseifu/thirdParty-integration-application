/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RequestInputUpdate from './request-input-update.vue';
import RequestInputService from './request-input.service';
import AlertService from '@/shared/alert/alert.service';

import OperationService from '@/entities/operation/operation.service';

type RequestInputUpdateComponentType = InstanceType<typeof RequestInputUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const requestInputSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RequestInputUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RequestInput Management Update Component', () => {
    let comp: RequestInputUpdateComponentType;
    let requestInputServiceStub: SinonStubbedInstance<RequestInputService>;

    beforeEach(() => {
      route = {};
      requestInputServiceStub = sinon.createStubInstance<RequestInputService>(RequestInputService);
      requestInputServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          requestInputService: () => requestInputServiceStub,
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
        const wrapper = shallowMount(RequestInputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.requestInput = requestInputSample;
        requestInputServiceStub.update.resolves(requestInputSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requestInputServiceStub.update.calledWith(requestInputSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        requestInputServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RequestInputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.requestInput = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requestInputServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        requestInputServiceStub.find.resolves(requestInputSample);
        requestInputServiceStub.retrieve.resolves([requestInputSample]);

        // WHEN
        route = {
          params: {
            requestInputId: '' + requestInputSample.id,
          },
        };
        const wrapper = shallowMount(RequestInputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.requestInput).toMatchObject(requestInputSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        requestInputServiceStub.find.resolves(requestInputSample);
        const wrapper = shallowMount(RequestInputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
