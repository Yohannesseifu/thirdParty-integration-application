/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import FormUi from './form-ui.vue';
import FormUiService from './form-ui.service';
import AlertService from '@/shared/alert/alert.service';

type FormUiComponentType = InstanceType<typeof FormUi>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('FormUi Management Component', () => {
    let formUiServiceStub: SinonStubbedInstance<FormUiService>;
    let mountOptions: MountingOptions<FormUiComponentType>['global'];

    beforeEach(() => {
      formUiServiceStub = sinon.createStubInstance<FormUiService>(FormUiService);
      formUiServiceStub.retrieve.resolves({ headers: {} });

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
          formUiService: () => formUiServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        formUiServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(FormUi, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(formUiServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.formUis[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: FormUiComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(FormUi, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        formUiServiceStub.retrieve.reset();
        formUiServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        formUiServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeFormUi();
        await comp.$nextTick(); // clear components

        // THEN
        expect(formUiServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(formUiServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
