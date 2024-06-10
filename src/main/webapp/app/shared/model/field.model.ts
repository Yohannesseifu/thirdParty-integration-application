import { type IFormUi } from '@/shared/model/form-ui.model';
import { type IFieldUIMetaData } from '@/shared/model/field-ui-meta-data.model';

import { type DataType } from '@/shared/model/enumerations/data-type.model';
export interface IField {
  id?: string;
  name?: string | null;
  dataType?: keyof typeof DataType | null;
  isUnique?: boolean | null;
  maxLength?: number | null;
  minLength?: number | null;
  minValue?: string | null;
  maxValue?: string | null;
  isRequired?: boolean | null;
  sortOrder?: number | null;
  formUi?: IFormUi | null;
  fieldUIMetaData?: IFieldUIMetaData | null;
}

export class Field implements IField {
  constructor(
    public id?: string,
    public name?: string | null,
    public dataType?: keyof typeof DataType | null,
    public isUnique?: boolean | null,
    public maxLength?: number | null,
    public minLength?: number | null,
    public minValue?: string | null,
    public maxValue?: string | null,
    public isRequired?: boolean | null,
    public sortOrder?: number | null,
    public formUi?: IFormUi | null,
    public fieldUIMetaData?: IFieldUIMetaData | null,
  ) {
    this.isUnique = this.isUnique ?? false;
    this.isRequired = this.isRequired ?? false;
  }
}
