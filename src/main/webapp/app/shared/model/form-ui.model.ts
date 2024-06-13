import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';

import { type FormType } from '@/shared/model/enumerations/form-type.model';
export interface IFormUi {
  id?: string;
  formName?: string | null;
  formDescription?: string | null;
  formType?: keyof typeof FormType | null;
  stepOrder?: number | null;
  thirdPartyIntegration?: IThirdPartyIntegration | null;
}

export class FormUi implements IFormUi {
  constructor(
    public id?: string,
    public formName?: string | null,
    public formDescription?: string | null,
    public formType?: keyof typeof FormType | null,
    public stepOrder?: number | null,
    public thirdPartyIntegration?: IThirdPartyIntegration | null,
  ) {}
}
