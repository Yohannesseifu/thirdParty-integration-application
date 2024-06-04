/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import FieldUIMetaData from './field-ui-meta-data.vue';
import FieldUIMetaDataService from './field-ui-meta-data.service';
import AlertService from '@/shared/alert/alert.service';

type FieldUIMetaDataComponentType = InstanceType<typeof FieldUIMetaData>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('FieldUIMetaData Management Component', () => {
    let fieldUIMetaDataServiceStub: SinonStubbedInstance<FieldUIMetaDataService>;
    let mountOptions: MountingOptions<FieldUIMetaDataComponentType>['global'];

    beforeEach(() => {
      fieldUIMetaDataServiceStub = sinon.createStubInstance<FieldUIMetaDataService>(FieldUIMetaDataService);
      fieldUIMetaDataServiceStub.retrieve.resolves({ headers: {} });

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
          fieldUIMetaDataService: () => fieldUIMetaDataServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fieldUIMetaDataServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(FieldUIMetaData, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(fieldUIMetaDataServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.fieldUIMetaData[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: FieldUIMetaDataComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(FieldUIMetaData, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        fieldUIMetaDataServiceStub.retrieve.reset();
        fieldUIMetaDataServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        fieldUIMetaDataServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeFieldUIMetaData();
        await comp.$nextTick(); // clear components

        // THEN
        expect(fieldUIMetaDataServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(fieldUIMetaDataServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
