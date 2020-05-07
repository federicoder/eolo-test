import { Moment } from 'moment';

export interface IStorageCloudFrontend {
  id?: number;
  idUtente?: number;
  idLicenza?: number;
  pianoBase?: string;
  dataCessione?: Moment;
  licenzaId?: number;
  professionistaId?: number;
}

export class StorageCloudFrontend implements IStorageCloudFrontend {
  constructor(
    public id?: number,
    public idUtente?: number,
    public idLicenza?: number,
    public pianoBase?: string,
    public dataCessione?: Moment,
    public licenzaId?: number,
    public professionistaId?: number
  ) {}
}
