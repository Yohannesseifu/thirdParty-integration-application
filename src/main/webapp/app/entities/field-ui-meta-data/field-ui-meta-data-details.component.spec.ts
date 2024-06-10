/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import FieldUIMetaDataDetails from './field-ui-meta-data-details.vue';
import FieldUIMetaDataService from './field-ui-meta-data.service';
import AlertService from '@/shared/alert/alert.service';

type FieldUIMetaDataDetailsComponentType = InstanceType<typeof FieldUIMetaDataDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const fieldUIMetaDataSample = { id: '9fec3727-3421-4967-b213-ba36557ca194' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('FieldUIMetaData Management Detail Component', () => {
    let fieldUIMetaDataServiceStub: SinonStubbedInstance<FieldUIMetaDataService>;
    let mountOptions: MountingOptions<FieldUIMetaDataDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      fieldUIMetaDataServiceStub = sinon.createStubInstance<FieldUIMetaDataService>(FieldUIMetaDataService);

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
          fieldUIMetaDataService: () => fieldUIMetaDataServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fieldUIMetaDataServiceStub.find.resolves(fieldUIMetaDataSample);
        route = {
          params: {
            fieldUIMetaDataId: '' + '9fec3727-3421-4967-b213-ba36557ca194',
          },
        };
        const wrapper = shallowMount(FieldUIMetaDataDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.fieldUIMetaData).toMatchObject(fieldUIMetaDataSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        fieldUIMetaDataServiceStub.find.resolves(fieldUIMetaDataSample);
        const wrapper = shallowMount(FieldUIMetaDataDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
