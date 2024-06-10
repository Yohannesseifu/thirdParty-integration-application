import { type IApiRequest } from '@/shared/model/api-request.model';
import { type IOperation } from '@/shared/model/operation.model';

export interface IPaymentDetail {
  id?: string;
  computedPaymentDetail?: string | null;
  apiRequest?: IApiRequest | null;
  operation?: IOperation | null;
}

export class PaymentDetail implements IPaymentDetail {
  constructor(
    public id?: string,
    public computedPaymentDetail?: string | null,
    public apiRequest?: IApiRequest | null,
    public operation?: IOperation | null,
  ) {}
}
