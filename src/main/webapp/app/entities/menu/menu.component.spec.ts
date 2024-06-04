/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Menu from './menu.vue';
import MenuService from './menu.service';
import AlertService from '@/shared/alert/alert.service';

type MenuComponentType = InstanceType<typeof Menu>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Menu Management Component', () => {
    let menuServiceStub: SinonStubbedInstance<MenuService>;
    let mountOptions: MountingOptions<MenuComponentType>['global'];

    beforeEach(() => {
      menuServiceStub = sinon.createStubInstance<MenuService>(MenuService);
      menuServiceStub.retrieve.resolves({ headers: {} });

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
          menuService: () => menuServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        menuServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Menu, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(menuServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.menus[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: MenuComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Menu, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        menuServiceStub.retrieve.reset();
        menuServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        menuServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeMenu();
        await comp.$nextTick(); // clear components

        // THEN
        expect(menuServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(menuServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
