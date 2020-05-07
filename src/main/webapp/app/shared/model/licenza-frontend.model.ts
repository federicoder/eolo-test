import { Moment } from 'moment';

export interface ILicenzaFrontend {
  id?: number;
  idLicenza?: number;
  tipologia?: string;
  descrizione?: string;
  dataScadenza?: Moment;
  professionistaId?: number;
  storageCloudId?: number;
}

export class LicenzaFrontend implements ILicenzaFrontend {
  constructor(
    public id?: number,
    public idLicenza?: number,
    public tipologia?: string,
    public descrizione?: string,
    public dataScadenza?: Moment,
    public professionistaId?: number,
    public storageCloudId?: number
  ) {}
}
