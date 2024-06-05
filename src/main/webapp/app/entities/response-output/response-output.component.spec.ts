/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import ResponseOutput from './response-output.vue';
import ResponseOutputService from './response-output.service';
import AlertService from '@/shared/alert/alert.service';

type ResponseOutputComponentType = InstanceType<typeof ResponseOutput>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('ResponseOutput Management Component', () => {
    let responseOutputServiceStub: SinonStubbedInstance<ResponseOutputService>;
    let mountOptions: MountingOptions<ResponseOutputComponentType>['global'];

    beforeEach(() => {
      responseOutputServiceStub = sinon.createStubInstance<ResponseOutputService>(ResponseOutputService);
      responseOutputServiceStub.retrieve.resolves({ headers: {} });

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
          responseOutputService: () => responseOutputServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        responseOutputServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(ResponseOutput, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(responseOutputServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.responseOutputs[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ResponseOutputComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(ResponseOutput, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        responseOutputServiceStub.retrieve.reset();
        responseOutputServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        responseOutputServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeResponseOutput();
        await comp.$nextTick(); // clear components

        // THEN
        expect(responseOutputServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(responseOutputServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
