export interface IFieldUIMetaData {
  id?: number;
  label?: string | null;
  inputInterface?: string | null;
  width?: string | null;
  note?: string | null;
  validationPattern?: string | null;
  options?: string | null;
  displayOptions?: string | null;
  conditions?: string | null;
  translations?: string | null;
}

export class FieldUIMetaData implements IFieldUIMetaData {
  constructor(
    public id?: number,
    public label?: string | null,
    public inputInterface?: string | null,
    public width?: string | null,
    public note?: string | null,
    public validationPattern?: string | null,
    public options?: string | null,
    public displayOptions?: string | null,
    public conditions?: string | null,
    public translations?: string | null,
  ) {}
}
