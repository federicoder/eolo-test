import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollaboratoreFrontend, CollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';
import { CollaboratoreFrontendService } from './collaboratore-frontend.service';

@Component({
  selector: 'jhi-collaboratore-frontend-update',
  templateUrl: './collaboratore-frontend-update.component.html'
})
export class CollaboratoreFrontendUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idCollaboratore: [null, [Validators.required]],
    nome: [],
    cognome: [],
    tipologia: [],
    idPratic: [null, [Validators.required]],
    idInvito: []
  });

  constructor(
    protected collaboratoreService: CollaboratoreFrontendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaboratore }) => {
      this.updateForm(collaboratore);
    });
  }

  updateForm(collaboratore: ICollaboratoreFrontend): void {
    this.editForm.patchValue({
      id: collaboratore.id,
      idCollaboratore: collaboratore.idCollaboratore,
      nome: collaboratore.nome,
      cognome: collaboratore.cognome,
      tipologia: collaboratore.tipologia,
      idPratic: collaboratore.idPratic,
      idInvito: collaboratore.idInvito
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collaboratore = this.createFromForm();
    if (collaboratore.id !== undefined) {
      this.subscribeToSaveResponse(this.collaboratoreService.update(collaboratore));
    } else {
      this.subscribeToSaveResponse(this.collaboratoreService.create(collaboratore));
    }
  }

  private createFromForm(): ICollaboratoreFrontend {
    return {
      ...new CollaboratoreFrontend(),
      id: this.editForm.get(['id'])!.value,
      idCollaboratore: this.editForm.get(['idCollaboratore'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cognome: this.editForm.get(['cognome'])!.value,
      tipologia: this.editForm.get(['tipologia'])!.value,
      idPratic: this.editForm.get(['idPratic'])!.value,
      idInvito: this.editForm.get(['idInvito'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollaboratoreFrontend>>): void {
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
}
