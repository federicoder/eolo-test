import { ICollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';
import { IClienteFrontend } from 'app/shared/model/cliente-frontend.model';

export interface IInvitoFrontend {
  id?: number;
  utenteIscritto?: boolean;
  idUtente?: number;
  idPratica?: number;
  idInvito?: number;
  idUtenteId?: number;
  idUtenteId?: number;
  idInvitos?: ICollaboratoreFrontend[];
  idPraticaId?: number;
  idUtenteId?: number;
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
    public idUtenteId?: number,
    public idUtenteId?: number,
    public idInvitos?: ICollaboratoreFrontend[],
    public idPraticaId?: number,
    public idUtenteId?: number,
    public idUtentes?: ICollaboratoreFrontend[],
    public idPraticas?: IClienteFrontend[]
  ) {
    this.utenteIscritto = this.utenteIscritto || false;
  }
}
