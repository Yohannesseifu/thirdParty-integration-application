/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Header from './header.vue';
import HeaderService from './header.service';
import AlertService from '@/shared/alert/alert.service';

type HeaderComponentType = InstanceType<typeof Header>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Header Management Component', () => {
    let headerServiceStub: SinonStubbedInstance<HeaderService>;
    let mountOptions: MountingOptions<HeaderComponentType>['global'];

    beforeEach(() => {
      headerServiceStub = sinon.createStubInstance<HeaderService>(HeaderService);
      headerServiceStub.retrieve.resolves({ headers: {} });

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
          headerService: () => headerServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        headerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(Header, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(headerServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.headers[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: HeaderComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Header, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        headerServiceStub.retrieve.reset();
        headerServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        headerServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removeHeader();
        await comp.$nextTick(); // clear components

        // THEN
        expect(headerServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(headerServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
