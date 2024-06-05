import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';
import { type IOperation } from '@/shared/model/operation.model';

import { type OperationType } from '@/shared/model/enumerations/operation-type.model';
export interface IIntegrationOperation {
  id?: number;
  operationType?: keyof typeof OperationType | null;
  thirdPartyIntegration?: IThirdPartyIntegration | null;
  operation?: IOperation | null;
}

export class IntegrationOperation implements IIntegrationOperation {
  constructor(
    public id?: number,
    public operationType?: keyof typeof OperationType | null,
    public thirdPartyIntegration?: IThirdPartyIntegration | null,
    public operation?: IOperation | null,
  ) {}
}
