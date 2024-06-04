/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MenuDetails from './menu-details.vue';
import MenuService from './menu.service';
import AlertService from '@/shared/alert/alert.service';

type MenuDetailsComponentType = InstanceType<typeof MenuDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const menuSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Menu Management Detail Component', () => {
    let menuServiceStub: SinonStubbedInstance<MenuService>;
    let mountOptions: MountingOptions<MenuDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      menuServiceStub = sinon.createStubInstance<MenuService>(MenuService);

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
          menuService: () => menuServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        menuServiceStub.find.resolves(menuSample);
        route = {
          params: {
            menuId: '' + 123,
          },
        };
        const wrapper = shallowMount(MenuDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.menu).toMatchObject(menuSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        menuServiceStub.find.resolves(menuSample);
        const wrapper = shallowMount(MenuDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
