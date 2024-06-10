import { type IThirdPartyIntegration } from '@/shared/model/third-party-integration.model';

export interface IMenu {
  id?: string;
  menuName?: string | null;
  menuDescription?: string | null;
  iconPath?: string | null;
  enabled?: boolean | null;
  dynamicPaymentMenus?: IThirdPartyIntegration[] | null;
  parent?: IMenu | null;
}

export class Menu implements IMenu {
  constructor(
    public id?: string,
    public menuName?: string | null,
    public menuDescription?: string | null,
    public iconPath?: string | null,
    public enabled?: boolean | null,
    public dynamicPaymentMenus?: IThirdPartyIntegration[] | null,
    public parent?: IMenu | null,
  ) {
    this.enabled = this.enabled ?? false;
  }
}
