/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import RequestInputService from './request-input.service';
import { RequestInput } from '@/shared/model/request-input.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('RequestInput Service', () => {
    let service: RequestInputService;
    let elemDefault;

    beforeEach(() => {
      service = new RequestInputService();
      elemDefault = new RequestInput(
        123,
        'AAAAAAA',
        'HEADER',
        'COLLECTION',
        'AAAAAAA',
        'AAAAAAA',
        'PHONE_NUMBER',
        false,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a RequestInput', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a RequestInput', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a RequestInput', async () => {
        const returnedFromService = Object.assign(
          {
            inputName: 'BBBBBB',
            inputType: 'BBBBBB',
            dataType: 'BBBBBB',
            testValue: 'BBBBBB',
            defaultValue: 'BBBBBB',
            autoUserValue: 'BBBBBB',
            isEncoded: true,
            maxLength: 1,
            minLength: 1,
            minValue: 'BBBBBB',
            maxValue: 'BBBBBB',
            validationPattern: 'BBBBBB',
            isRequired: true,
          },
          elemDefault,
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a RequestInput', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a RequestInput', async () => {
        const patchObject = Object.assign(
          {
            defaultValue: 'BBBBBB',
            autoUserValue: 'BBBBBB',
            minValue: 'BBBBBB',
            maxValue: 'BBBBBB',
            validationPattern: 'BBBBBB',
          },
          new RequestInput(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a RequestInput', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of RequestInput', async () => {
        const returnedFromService = Object.assign(
          {
            inputName: 'BBBBBB',
            inputType: 'BBBBBB',
            dataType: 'BBBBBB',
            testValue: 'BBBBBB',
            defaultValue: 'BBBBBB',
            autoUserValue: 'BBBBBB',
            isEncoded: true,
            maxLength: 1,
            minLength: 1,
            minValue: 'BBBBBB',
            maxValue: 'BBBBBB',
            validationPattern: 'BBBBBB',
            isRequired: true,
          },
          elemDefault,
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of RequestInput', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a RequestInput', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a RequestInput', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});