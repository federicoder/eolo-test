import { Moment } from 'moment';
import { IPraticaFrontend } from 'app/shared/model/pratica-frontend.model';

export interface ILicenzaFrontend {
  id?: number;
  idLicenza?: number;
  tipologia?: string;
  descrizione?: string;
  dataScadenza?: Moment;
  idLicenzaId?: number;
  idLicenzaId?: number;
  idLicenzas?: IPraticaFrontend[];
}

export class LicenzaFrontend implements ILicenzaFrontend {
  constructor(
    public id?: number,
    public idLicenza?: number,
    public tipologia?: string,
    public descrizione?: string,
    public dataScadenza?: Moment,
    public idLicenzaId?: number,
    public idLicenzaId?: number,
    public idLicenzas?: IPraticaFrontend[]
  ) {}
}
