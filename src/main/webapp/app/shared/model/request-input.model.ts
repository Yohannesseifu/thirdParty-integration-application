import { type IOperation } from '@/shared/model/operation.model';

import { type RequestInputType } from '@/shared/model/enumerations/request-input-type.model';
import { type DataType } from '@/shared/model/enumerations/data-type.model';
import { type AutoUserValue } from '@/shared/model/enumerations/auto-user-value.model';
export interface IRequestInput {
  id?: number;
  inputName?: string;
  inputType?: keyof typeof RequestInputType;
  dataType?: keyof typeof DataType;
  testValue?: string | null;
  defaultValue?: string | null;
  autoUserValue?: keyof typeof AutoUserValue | null;
  isEncoded?: boolean | null;
  maxLength?: number | null;
  minLength?: number | null;
  minValue?: string | null;
  maxValue?: string | null;
  validationPattern?: string | null;
  isRequired?: boolean | null;
  operation?: IOperation | null;
}

export class RequestInput implements IRequestInput {
  constructor(
    public id?: number,
    public inputName?: string,
    public inputType?: keyof typeof RequestInputType,
    public dataType?: keyof typeof DataType,
    public testValue?: string | null,
    public defaultValue?: string | null,
    public autoUserValue?: keyof typeof AutoUserValue | null,
    public isEncoded?: boolean | null,
    public maxLength?: number | null,
    public minLength?: number | null,
    public minValue?: string | null,
    public maxValue?: string | null,
    public validationPattern?: string | null,
    public isRequired?: boolean | null,
    public operation?: IOperation | null,
  ) {
    this.isEncoded = this.isEncoded ?? false;
    this.isRequired = this.isRequired ?? false;
  }
}