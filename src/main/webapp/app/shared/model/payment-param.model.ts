import { type IPaymentDetail } from '@/shared/model/payment-detail.model';

import { type PaymentParamType } from '@/shared/model/enumerations/payment-param-type.model';
import { type DataType } from '@/shared/model/enumerations/data-type.model';
export interface IPaymentParam {
  id?: string;
  type?: keyof typeof PaymentParamType | null;
  name?: string | null;
  value?: string | null;
  dataType?: keyof typeof DataType;
  paymentDetail?: IPaymentDetail | null;
}

export class PaymentParam implements IPaymentParam {
  constructor(
    public id?: string,
    public type?: keyof typeof PaymentParamType | null,
    public name?: string | null,
    public value?: string | null,
    public dataType?: keyof typeof DataType,
    public paymentDetail?: IPaymentDetail | null,
  ) {}
}
