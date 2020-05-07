export interface IInvitoFrontend {
  id?: number;
  utenteIscritto?: boolean;
  idUtente?: number;
  idPratica?: number;
  idInvito?: number;
  collaboratoreId?: number;
  clienteId?: number;
}

export class InvitoFrontend implements IInvitoFrontend {
  constructor(
    public id?: number,
    public utenteIscritto?: boolean,
    public idUtente?: number,
    public idPratica?: number,
    public idInvito?: number,
    public collaboratoreId?: number,
    public clienteId?: number
  ) {
    this.utenteIscritto = this.utenteIscritto || false;
  }
}
