import axios from 'axios';

import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';

const baseApiUrl = 'api/third-party-integrations';

export default class ThirdPartyIntegrationService {
  public find(id: number): Promise<IThirdPartyIntegration> {
    return new Promise<IThirdPartyIntegration>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IThirdPartyIntegration): Promise<IThirdPartyIntegration> {
    return new Promise<IThirdPartyIntegration>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IThirdPartyIntegration): Promise<IThirdPartyIntegration> {
    return new Promise<IThirdPartyIntegration>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IThirdPartyIntegration): Promise<IThirdPartyIntegration> {
    return new Promise<IThirdPartyIntegration>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
