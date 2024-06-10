import { type HttpMethod } from '@/shared/model/enumerations/http-method.model';
export interface IApiRequest {
  id?: string;
  uri?: string;
  body?: string | null;
  method?: keyof typeof HttpMethod;
}

export class ApiRequest implements IApiRequest {
  constructor(
    public id?: string,
    public uri?: string,
    public body?: string | null,
    public method?: keyof typeof HttpMethod,
  ) {}
}
