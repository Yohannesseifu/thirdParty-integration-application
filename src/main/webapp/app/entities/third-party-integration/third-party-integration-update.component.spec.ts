/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ThirdPartyIntegrationUpdate from './third-party-integration-update.vue';
import ThirdPartyIntegrationService from './third-party-integration.service';
import AlertService from '@/shared/alert/alert.service';

import MenuService from '@/entities/menu/menu.service';

type ThirdPartyIntegrationUpdateComponentType = InstanceType<typeof ThirdPartyIntegrationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const thirdPartyIntegrationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ThirdPartyIntegrationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('ThirdPartyIntegration Management Update Component', () => {
    let comp: ThirdPartyIntegrationUpdateComponentType;
    let thirdPartyIntegrationServiceStub: SinonStubbedInstance<ThirdPartyIntegrationService>;

    beforeEach(() => {
      route = {};
      thirdPartyIntegrationServiceStub = sinon.createStubInstance<ThirdPartyIntegrationService>(ThirdPartyIntegrationService);
      thirdPartyIntegrationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          thirdPartyIntegrationService: () => thirdPartyIntegrationServiceStub,
          menuService: () =>
            sinon.createStubInstance<MenuService>(MenuService, {
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
        const wrapper = shallowMount(ThirdPartyIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.thirdPartyIntegration = thirdPartyIntegrationSample;
        thirdPartyIntegrationServiceStub.update.resolves(thirdPartyIntegrationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(thirdPartyIntegrationServiceStub.update.calledWith(thirdPartyIntegrationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        thirdPartyIntegrationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ThirdPartyIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.thirdPartyIntegration = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(thirdPartyIntegrationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        thirdPartyIntegrationServiceStub.find.resolves(thirdPartyIntegrationSample);
        thirdPartyIntegrationServiceStub.retrieve.resolves([thirdPartyIntegrationSample]);

        // WHEN
        route = {
          params: {
            thirdPartyIntegrationId: '' + thirdPartyIntegrationSample.id,
          },
        };
        const wrapper = shallowMount(ThirdPartyIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.thirdPartyIntegration).toMatchObject(thirdPartyIntegrationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        thirdPartyIntegrationServiceStub.find.resolves(thirdPartyIntegrationSample);
        const wrapper = shallowMount(ThirdPartyIntegrationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
