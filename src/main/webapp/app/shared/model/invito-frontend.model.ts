import { ICollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';
import { IClienteFrontend } from 'app/shared/model/cliente-frontend.model';

export interface IInvitoFrontend {
  id?: number;
  utenteIscritto?: boolean;
  idUtente?: number;
  idPratica?: number;
  idInvito?: number;
  collaboratoreId?: number;
  clienteId?: number;
  collaboratores?: ICollaboratoreFrontend[];
  praticaId?: number;
  professionistaId?: number;
  idUtentes?: ICollaboratoreFrontend[];
  idPraticas?: IClienteFrontend[];
}

export class InvitoFrontend implements IInvitoFrontend {
  constructor(
    public id?: number,
    public utenteIscritto?: boolean,
    public idUtente?: number,
    public idPratica?: number,
    public idInvito?: number,
    public collaboratoreId?: number,
    public clienteId?: number,
    public collaboratores?: ICollaboratoreFrontend[],
    public praticaId?: number,
    public professionistaId?: number,
    public idUtentes?: ICollaboratoreFrontend[],
    public idPraticas?: IClienteFrontend[]
  ) {
    this.utenteIscritto = this.utenteIscritto || false;
  }
}
