/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FieldDetails from './field-details.vue';
import FieldService from './field.service';
import AlertService from '@/shared/alert/alert.service';

type FieldDetailsComponentType = InstanceType<typeof FieldDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fieldSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Field Management Detail Component', () => {
    let fieldServiceStub: SinonStubbedInstance<FieldService>;
    let mountOptions: MountingOptions<FieldDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      fieldServiceStub = sinon.createStubInstance<FieldService>(FieldService);

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
          fieldService: () => fieldServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fieldServiceStub.find.resolves(fieldSample);
        route = {
          params: {
            fieldId: '' + 123,
          },
        };
        const wrapper = shallowMount(FieldDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.field).toMatchObject(fieldSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fieldServiceStub.find.resolves(fieldSample);
        const wrapper = shallowMount(FieldDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
