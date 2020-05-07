import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IInvitoFrontend, InvitoFrontend } from 'app/shared/model/invito-frontend.model';
import { InvitoFrontendService } from './invito-frontend.service';
import { ICollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';
import { CollaboratoreFrontendService } from 'app/entities/collaboratore-frontend/collaboratore-frontend.service';
import { IClienteFrontend } from 'app/shared/model/cliente-frontend.model';
import { ClienteFrontendService } from 'app/entities/cliente-frontend/cliente-frontend.service';

type SelectableEntity = ICollaboratoreFrontend | IClienteFrontend;

@Component({
  selector: 'jhi-invito-frontend-update',
  templateUrl: './invito-frontend-update.component.html'
})
export class InvitoFrontendUpdateComponent implements OnInit {
  isSaving = false;
  collaboratores: ICollaboratoreFrontend[] = [];
  clientes: IClienteFrontend[] = [];

  editForm = this.fb.group({
    id: [],
    utenteIscritto: [],
    idUtente: [],
    idPratica: [],
    idInvito: [null, [Validators.required]],
    collaboratoreId: [],
    clienteId: []
  });

  constructor(
    protected invitoService: InvitoFrontendService,
    protected collaboratoreService: CollaboratoreFrontendService,
    protected clienteService: ClienteFrontendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invito }) => {
      this.updateForm(invito);

      this.collaboratoreService
        .query({ filter: 'invito-is-null' })
        .pipe(
          map((res: HttpResponse<ICollaboratoreFrontend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICollaboratoreFrontend[]) => {
          if (!invito.collaboratoreId) {
            this.collaboratores = resBody;
          } else {
            this.collaboratoreService
              .find(invito.collaboratoreId)
              .pipe(
                map((subRes: HttpResponse<ICollaboratoreFrontend>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICollaboratoreFrontend[]) => (this.collaboratores = concatRes));
          }
        });

      this.clienteService
        .query({ filter: 'invito-is-null' })
        .pipe(
          map((res: HttpResponse<IClienteFrontend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IClienteFrontend[]) => {
          if (!invito.clienteId) {
            this.clientes = resBody;
          } else {
            this.clienteService
              .find(invito.clienteId)
              .pipe(
                map((subRes: HttpResponse<IClienteFrontend>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IClienteFrontend[]) => (this.clientes = concatRes));
          }
        });
    });
  }

  updateForm(invito: IInvitoFrontend): void {
    this.editForm.patchValue({
      id: invito.id,
      utenteIscritto: invito.utenteIscritto,
      idUtente: invito.idUtente,
      idPratica: invito.idPratica,
      idInvito: invito.idInvito,
      collaboratoreId: invito.collaboratoreId,
      clienteId: invito.clienteId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invito = this.createFromForm();
    if (invito.id !== undefined) {
      this.subscribeToSaveResponse(this.invitoService.update(invito));
    } else {
      this.subscribeToSaveResponse(this.invitoService.create(invito));
    }
  }

  private createFromForm(): IInvitoFrontend {
    return {
      ...new InvitoFrontend(),
      id: this.editForm.get(['id'])!.value,
      utenteIscritto: this.editForm.get(['utenteIscritto'])!.value,
      idUtente: this.editForm.get(['idUtente'])!.value,
      idPratica: this.editForm.get(['idPratica'])!.value,
      idInvito: this.editForm.get(['idInvito'])!.value,
      collaboratoreId: this.editForm.get(['collaboratoreId'])!.value,
      clienteId: this.editForm.get(['clienteId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvitoFrontend>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
