import { Moment } from 'moment';
import { IPraticaFrontend } from 'app/shared/model/pratica-frontend.model';

export interface ILicenzaFrontend {
  id?: number;
  idLicenza?: number;
  tipologia?: string;
  descrizione?: string;
  dataScadenza?: Moment;
  professionistaId?: number;
  storageCloudId?: number;
  praticas?: IPraticaFrontend[];
}

export class LicenzaFrontend implements ILicenzaFrontend {
  constructor(
    public id?: number,
    public idLicenza?: number,
    public tipologia?: string,
    public descrizione?: string,
    public dataScadenza?: Moment,
    public professionistaId?: number,
    public storageCloudId?: number,
    public praticas?: IPraticaFrontend[]
  ) {}
}
