/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FormUiDetails from './form-ui-details.vue';
import FormUiService from './form-ui.service';
import AlertService from '@/shared/alert/alert.service';

type FormUiDetailsComponentType = InstanceType<typeof FormUiDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const formUiSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('FormUi Management Detail Component', () => {
    let formUiServiceStub: SinonStubbedInstance<FormUiService>;
    let mountOptions: MountingOptions<FormUiDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      formUiServiceStub = sinon.createStubInstance<FormUiService>(FormUiService);

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
          formUiService: () => formUiServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        formUiServiceStub.find.resolves(formUiSample);
        route = {
          params: {
            formUiId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(FormUiDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.formUi).toMatchObject(formUiSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        formUiServiceStub.find.resolves(formUiSample);
        const wrapper = shallowMount(FormUiDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
