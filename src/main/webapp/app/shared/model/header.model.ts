import { type IApiRequest } from '@/shared/model/api-request.model';

export interface IHeader {
  id?: string;
  key?: string;
  value?: string;
  apiRequest?: IApiRequest | null;
}

export class Header implements IHeader {
  constructor(
    public id?: string,
    public key?: string,
    public value?: string,
    public apiRequest?: IApiRequest | null,
  ) {}
}
