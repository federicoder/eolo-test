export interface IPraticaFrontend {
  id?: number;
  idPratica?: number;
  idLic?: number;
  tdp?: number;
  idCollab?: number;
  idClient?: number;
}

export class PraticaFrontend implements IPraticaFrontend {
  constructor(
    public id?: number,
    public idPratica?: number,
    public idLic?: number,
    public tdp?: number,
    public idCollab?: number,
    public idClient?: number
  ) {}
}
