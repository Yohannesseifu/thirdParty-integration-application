import { type ContentType } from '@/shared/model/enumerations/content-type.model';
import { type AuthType } from '@/shared/model/enumerations/auth-type.model';
export interface IApiIntegration {
  id?: number;
  name?: string;
  url?: string;
  type?: keyof typeof ContentType;
  auth?: keyof typeof AuthType;
  description?: string | null;
  version?: string | null;
  timeout?: number | null;
  retryRetries?: number | null;
  retryDelay?: number | null;
}

export class ApiIntegration implements IApiIntegration {
  constructor(
    public id?: number,
    public name?: string,
    public url?: string,
    public type?: keyof typeof ContentType,
    public auth?: keyof typeof AuthType,
    public description?: string | null,
    public version?: string | null,
    public timeout?: number | null,
    public retryRetries?: number | null,
    public retryDelay?: number | null,
  ) {}
}
