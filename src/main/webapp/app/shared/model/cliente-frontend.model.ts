export interface IClienteFrontend {
  id?: number;
  idCliente?: number;
  nome?: string;
  cognome?: string;
  idPraticaConnessa?: number;
  invitoId?: number;
}

export class ClienteFrontend implements IClienteFrontend {
  constructor(
    public id?: number,
    public idCliente?: number,
    public nome?: string,
    public cognome?: string,
    public idPraticaConnessa?: number,
    public invitoId?: number
  ) {}
}
