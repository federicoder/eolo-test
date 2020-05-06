import { IInvitoFrontend } from 'app/shared/model/invito-frontend.model';

export interface IClienteFrontend {
  id?: number;
  idCliente?: number;
  nome?: string;
  cognome?: string;
  idPraticaConnessa?: number;
  idClientes?: IInvitoFrontend[];
  idUtenteId?: number;
}

export class ClienteFrontend implements IClienteFrontend {
  constructor(
    public id?: number,
    public idCliente?: number,
    public nome?: string,
    public cognome?: string,
    public idPraticaConnessa?: number,
    public idClientes?: IInvitoFrontend[],
    public idUtenteId?: number
  ) {}
}
