import { IInvitoFrontend } from 'app/shared/model/invito-frontend.model';

export interface ICollaboratoreFrontend {
  id?: number;
  idCollaboratore?: number;
  nome?: string;
  cognome?: string;
  tipologia?: string;
  idPratic?: number;
  idInvito?: number;
  idCollaboratores?: IInvitoFrontend[];
  invitoId?: number;
  invitoId?: number;
}

export class CollaboratoreFrontend implements ICollaboratoreFrontend {
  constructor(
    public id?: number,
    public idCollaboratore?: number,
    public nome?: string,
    public cognome?: string,
    public tipologia?: string,
    public idPratic?: number,
    public idInvito?: number,
    public idCollaboratores?: IInvitoFrontend[],
    public invitoId?: number,
    public invitoId?: number
  ) {}
}
