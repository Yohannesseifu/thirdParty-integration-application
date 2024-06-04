import { type IOperation } from '@/shared/model/operation.model';

import { type DataType } from '@/shared/model/enumerations/data-type.model';
import { type Scope } from '@/shared/model/enumerations/scope.model';
import { type CoreMapping } from '@/shared/model/enumerations/core-mapping.model';
export interface IResponseOutput {
  id?: number;
  outputName?: string;
  dataType?: keyof typeof DataType;
  responseValuePath?: string;
  responseScope?: keyof typeof Scope;
  coreMapping?: keyof typeof CoreMapping | null;
  expectedValue?: string | null;
  operation?: IOperation | null;
}

export class ResponseOutput implements IResponseOutput {
  constructor(
    public id?: number,
    public outputName?: string,
    public dataType?: keyof typeof DataType,
    public responseValuePath?: string,
    public responseScope?: keyof typeof Scope,
    public coreMapping?: keyof typeof CoreMapping | null,
    public expectedValue?: string | null,
    public operation?: IOperation | null,
  ) {}
}
