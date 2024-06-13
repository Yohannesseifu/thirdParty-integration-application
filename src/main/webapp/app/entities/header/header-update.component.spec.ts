/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import HeaderUpdate from './header-update.vue';
import HeaderService from './header.service';
import AlertService from '@/shared/alert/alert.service';

import ApiRequestService from '@/entities/api-request/api-request.service';

type HeaderUpdateComponentType = InstanceType<typeof HeaderUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const headerSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<HeaderUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Header Management Update Component', () => {
    let comp: HeaderUpdateComponentType;
    let headerServiceStub: SinonStubbedInstance<HeaderService>;

    beforeEach(() => {
      route = {};
      headerServiceStub = sinon.createStubInstance<HeaderService>(HeaderService);
      headerServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          headerService: () => headerServiceStub,
          apiRequestService: () =>
            sinon.createStubInstance<ApiRequestService>(ApiRequestService, {
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
        const wrapper = shallowMount(HeaderUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.header = headerSample;
        headerServiceStub.update.resolves(headerSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(headerServiceStub.update.calledWith(headerSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        headerServiceStub.create.resolves(entity);
        const wrapper = shallowMount(HeaderUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.header = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(headerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        headerServiceStub.find.resolves(headerSample);
        headerServiceStub.retrieve.resolves([headerSample]);

        // WHEN
        route = {
          params: {
            headerId: '' + headerSample.id,
          },
        };
        const wrapper = shallowMount(HeaderUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.header).toMatchObject(headerSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        headerServiceStub.find.resolves(headerSample);
        const wrapper = shallowMount(HeaderUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
