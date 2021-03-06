import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPraticaFrontend, PraticaFrontend } from 'app/shared/model/pratica-frontend.model';
import { PraticaFrontendService } from './pratica-frontend.service';

@Component({
  selector: 'jhi-pratica-frontend-update',
  templateUrl: './pratica-frontend-update.component.html'
})
export class PraticaFrontendUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idPratica: [null, [Validators.required]],
    idLic: [null, [Validators.required]],
    tdp: [null, [Validators.required]],
    idCollab: [],
    idClient: [null, [Validators.required]]
  });

  constructor(protected praticaService: PraticaFrontendService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pratica }) => {
      this.updateForm(pratica);
    });
  }

  updateForm(pratica: IPraticaFrontend): void {
    this.editForm.patchValue({
      id: pratica.id,
      idPratica: pratica.idPratica,
      idLic: pratica.idLic,
      tdp: pratica.tdp,
      idCollab: pratica.idCollab,
      idClient: pratica.idClient
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pratica = this.createFromForm();
    if (pratica.id !== undefined) {
      this.subscribeToSaveResponse(this.praticaService.update(pratica));
    } else {
      this.subscribeToSaveResponse(this.praticaService.create(pratica));
    }
  }

  private createFromForm(): IPraticaFrontend {
    return {
      ...new PraticaFrontend(),
      id: this.editForm.get(['id'])!.value,
      idPratica: this.editForm.get(['idPratica'])!.value,
      idLic: this.editForm.get(['idLic'])!.value,
      tdp: this.editForm.get(['tdp'])!.value,
      idCollab: this.editForm.get(['idCollab'])!.value,
      idClient: this.editForm.get(['idClient'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPraticaFrontend>>): void {
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
