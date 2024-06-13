import { type IApiRequest } from '@/shared/model/api-request.model';

export interface IHeader {
  id?: string;
  name?: string;
  valueStr?: string;
  apiRequest?: IApiRequest | null;
}

export class Header implements IHeader {
  constructor(
    public id?: string,
    public name?: string,
    public valueStr?: string,
    public apiRequest?: IApiRequest | null,
  ) {}
}
