import { type IApiIntegration } from '@/shared/model/api-integration.model';

import { type HttpMethod } from '@/shared/model/enumerations/http-method.model';
export interface IOperation {
  id?: number;
  operationName?: string;
  httpMethod?: keyof typeof HttpMethod;
  endpointPath?: string;
  requestBodyTemplate?: string | null;
  apiIntegration?: IApiIntegration | null;
}

export class Operation implements IOperation {
  constructor(
    public id?: number,
    public operationName?: string,
    public httpMethod?: keyof typeof HttpMethod,
    public endpointPath?: string,
    public requestBodyTemplate?: string | null,
    public apiIntegration?: IApiIntegration | null,
  ) {}
}
