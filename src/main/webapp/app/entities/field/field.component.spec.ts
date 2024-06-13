/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Field from './field.vue';
import FieldService from './field.service';
import AlertService from '@/shared/alert/alert.service';

type FieldComponentType = InstanceType<typeof Field>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Field Management Component', () => {
    let fieldServiceStub: SinonStubbedInstance<FieldService>;
    let mountOptions: MountingOptions<FieldComponentType>['global'];

    beforeEach(() => {
      fieldServiceStub = sinon.createStubInstance<FieldService>(FieldService);
      fieldServiceStub.retrieve.resolves({ headers: {} });

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
          fieldService: () => fieldServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fieldServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

        // WHEN
        const wrapper = shallowMount(Field, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(fieldServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.fields[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
    describe('Handles', () => {
      let comp: FieldComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Field, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        fieldServiceStub.retrieve.reset();
        fieldServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        fieldServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });

        comp.removeField();
        await comp.$nextTick(); // clear components

        // THEN
        expect(fieldServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(fieldServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
