import { type IIntegrationOperation } from '@/shared/model/integration-operation.model';
import { type IField } from '@/shared/model/field.model';
import { type IRequestInput } from '@/shared/model/request-input.model';

export interface IIntegrationFieldMapping {
  id?: string;
  integrationOperation?: IIntegrationOperation | null;
  field?: IField | null;
  requestInput?: IRequestInput | null;
}

export class IntegrationFieldMapping implements IIntegrationFieldMapping {
  constructor(
    public id?: string,
    public integrationOperation?: IIntegrationOperation | null,
    public field?: IField | null,
    public requestInput?: IRequestInput | null,
  ) {}
}
