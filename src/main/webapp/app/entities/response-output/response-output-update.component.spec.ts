/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ResponseOutputUpdate from './response-output-update.vue';
import ResponseOutputService from './response-output.service';
import AlertService from '@/shared/alert/alert.service';

import OperationService from '@/entities/operation/operation.service';

type ResponseOutputUpdateComponentType = InstanceType<typeof ResponseOutputUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const responseOutputSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ResponseOutputUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ResponseOutput Management Update Component', () => {
    let comp: ResponseOutputUpdateComponentType;
    let responseOutputServiceStub: SinonStubbedInstance<ResponseOutputService>;

    beforeEach(() => {
      route = {};
      responseOutputServiceStub = sinon.createStubInstance<ResponseOutputService>(ResponseOutputService);
      responseOutputServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          responseOutputService: () => responseOutputServiceStub,
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
        const wrapper = shallowMount(ResponseOutputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.responseOutput = responseOutputSample;
        responseOutputServiceStub.update.resolves(responseOutputSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(responseOutputServiceStub.update.calledWith(responseOutputSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        responseOutputServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ResponseOutputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.responseOutput = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(responseOutputServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        responseOutputServiceStub.find.resolves(responseOutputSample);
        responseOutputServiceStub.retrieve.resolves([responseOutputSample]);

        // WHEN
        route = {
          params: {
            responseOutputId: '' + responseOutputSample.id,
          },
        };
        const wrapper = shallowMount(ResponseOutputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.responseOutput).toMatchObject(responseOutputSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        responseOutputServiceStub.find.resolves(responseOutputSample);
        const wrapper = shallowMount(ResponseOutputUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
