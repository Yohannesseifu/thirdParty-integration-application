/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FormUiUpdate from './form-ui-update.vue';
import FormUiService from './form-ui.service';
import AlertService from '@/shared/alert/alert.service';

import ThirdPartyIntegrationService from '@/entities/third-party-integration/third-party-integration.service';

type FormUiUpdateComponentType = InstanceType<typeof FormUiUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const formUiSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<FormUiUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('FormUi Management Update Component', () => {
    let comp: FormUiUpdateComponentType;
    let formUiServiceStub: SinonStubbedInstance<FormUiService>;

    beforeEach(() => {
      route = {};
      formUiServiceStub = sinon.createStubInstance<FormUiService>(FormUiService);
      formUiServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          formUiService: () => formUiServiceStub,
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
        const wrapper = shallowMount(FormUiUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.formUi = formUiSample;
        formUiServiceStub.update.resolves(formUiSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(formUiServiceStub.update.calledWith(formUiSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        formUiServiceStub.create.resolves(entity);
        const wrapper = shallowMount(FormUiUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.formUi = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(formUiServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        formUiServiceStub.find.resolves(formUiSample);
        formUiServiceStub.retrieve.resolves([formUiSample]);

        // WHEN
        route = {
          params: {
            formUiId: '' + formUiSample.id,
          },
        };
        const wrapper = shallowMount(FormUiUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.formUi).toMatchObject(formUiSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        formUiServiceStub.find.resolves(formUiSample);
        const wrapper = shallowMount(FormUiUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
