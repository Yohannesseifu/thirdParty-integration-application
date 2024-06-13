import { type IOperation } from '@/shared/model/operation.model';

import { type DataType } from '@/shared/model/enumerations/data-type.model';
import { type Scope } from '@/shared/model/enumerations/scope.model';
import { type CoreTransferParams } from '@/shared/model/enumerations/core-transfer-params.model';
import { type LogicalOperator } from '@/shared/model/enumerations/logical-operator.model';
export interface IResponseOutput {
  id?: string;
  outputName?: string;
  dataType?: keyof typeof DataType;
  responseValuePath?: string;
  responseScope?: keyof typeof Scope;
  transferCoreMapping?: keyof typeof CoreTransferParams | null;
  isLogicField?: boolean | null;
  constantValueToCompare?: string | null;
  operatorToCompareValue?: keyof typeof LogicalOperator | null;
  isRequired?: boolean | null;
  operation?: IOperation | null;
}

export class ResponseOutput implements IResponseOutput {
  constructor(
    public id?: string,
    public outputName?: string,
    public dataType?: keyof typeof DataType,
    public responseValuePath?: string,
    public responseScope?: keyof typeof Scope,
    public transferCoreMapping?: keyof typeof CoreTransferParams | null,
    public isLogicField?: boolean | null,
    public constantValueToCompare?: string | null,
    public operatorToCompareValue?: keyof typeof LogicalOperator | null,
    public isRequired?: boolean | null,
    public operation?: IOperation | null,
  ) {
    this.isLogicField = this.isLogicField ?? false;
    this.isRequired = this.isRequired ?? false;
  }
}
