export interface IProfessionistaFrontend {
  id?: number;
  idProfessionista?: number;
  nome?: string;
  cognome?: string;
  tipologia?: string;
  codiceFiscale?: string;
  pIva?: string;
  studioAssociato?: string;
  idLicenza?: number;
  storageCloudId?: number;
  licenzaId?: number;
}

export class ProfessionistaFrontend implements IProfessionistaFrontend {
  constructor(
    public id?: number,
    public idProfessionista?: number,
    public nome?: string,
    public cognome?: string,
    public tipologia?: string,
    public codiceFiscale?: string,
    public pIva?: string,
    public studioAssociato?: string,
    public idLicenza?: number,
    public storageCloudId?: number,
    public licenzaId?: number
  ) {}
}
