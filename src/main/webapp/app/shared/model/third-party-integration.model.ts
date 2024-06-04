import { type IMenu } from '@/shared/model/menu.model';

import { type IntegrationType } from '@/shared/model/enumerations/integration-type.model';
import { type Visiblity } from '@/shared/model/enumerations/visiblity.model';
export interface IThirdPartyIntegration {
  id?: number;
  isDraft?: boolean | null;
  integrationName?: string | null;
  companyName?: string | null;
  description?: string | null;
  iconPath?: string | null;
  enabled?: boolean | null;
  accountNumber?: string | null;
  minimumAmount?: number | null;
  maximumAmount?: number | null;
  currencyCode?: string | null;
  paymentConfirmationTemplate?: string | null;
  paymentDetailTemplate?: string | null;
  integrationCategory?: keyof typeof IntegrationType | null;
  visiblity?: keyof typeof Visiblity | null;
  confirmRecipientIdentity?: boolean | null;
  categoryMenus?: IMenu[] | null;
}

export class ThirdPartyIntegration implements IThirdPartyIntegration {
  constructor(
    public id?: number,
    public isDraft?: boolean | null,
    public integrationName?: string | null,
    public companyName?: string | null,
    public description?: string | null,
    public iconPath?: string | null,
    public enabled?: boolean | null,
    public accountNumber?: string | null,
    public minimumAmount?: number | null,
    public maximumAmount?: number | null,
    public currencyCode?: string | null,
    public paymentConfirmationTemplate?: string | null,
    public paymentDetailTemplate?: string | null,
    public integrationCategory?: keyof typeof IntegrationType | null,
    public visiblity?: keyof typeof Visiblity | null,
    public confirmRecipientIdentity?: boolean | null,
    public categoryMenus?: IMenu[] | null,
  ) {
    this.isDraft = this.isDraft ?? false;
    this.enabled = this.enabled ?? false;
    this.confirmRecipientIdentity = this.confirmRecipientIdentity ?? false;
  }
}
