/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MenuUpdate from './menu-update.vue';
import MenuService from './menu.service';
import AlertService from '@/shared/alert/alert.service';

import ThirdPartyIntegrationService from '@/entities/third-party-integration/third-party-integration.service';

type MenuUpdateComponentType = InstanceType<typeof MenuUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const menuSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MenuUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Menu Management Update Component', () => {
    let comp: MenuUpdateComponentType;
    let menuServiceStub: SinonStubbedInstance<MenuService>;

    beforeEach(() => {
      route = {};
      menuServiceStub = sinon.createStubInstance<MenuService>(MenuService);
      menuServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          menuService: () => menuServiceStub,
          thirdPartyIntegrationService: () =>
            sinon.createStubInstance<ThirdPartyIntegrationService>(ThirdPartyIntegrationService, {
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
        const wrapper = shallowMount(MenuUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.menu = menuSample;
        menuServiceStub.update.resolves(menuSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(menuServiceStub.update.calledWith(menuSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        menuServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MenuUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.menu = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(menuServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        menuServiceStub.find.resolves(menuSample);
        menuServiceStub.retrieve.resolves([menuSample]);

        // WHEN
        route = {
          params: {
            menuId: '' + menuSample.id,
          },
        };
        const wrapper = shallowMount(MenuUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.menu).toMatchObject(menuSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        menuServiceStub.find.resolves(menuSample);
        const wrapper = shallowMount(MenuUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
