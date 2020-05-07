import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProfessionistaFrontend, ProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';
import { ProfessionistaFrontendService } from './professionista-frontend.service';
import { IStorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';
import { StorageCloudFrontendService } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend.service';

@Component({
  selector: 'jhi-professionista-frontend-update',
  templateUrl: './professionista-frontend-update.component.html'
})
export class ProfessionistaFrontendUpdateComponent implements OnInit {
  isSaving = false;
  storageclouds: IStorageCloudFrontend[] = [];

  editForm = this.fb.group({
    id: [],
    idProfessionista: [null, [Validators.required]],
    nome: [],
    cognome: [],
    tipologia: [],
    codiceFiscale: [null, [Validators.required]],
    pIva: [],
    studioAssociato: [],
    idLicenza: [],
    storageCloudId: []
  });

  constructor(
    protected professionistaService: ProfessionistaFrontendService,
    protected storageCloudService: StorageCloudFrontendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professionista }) => {
      this.updateForm(professionista);

      this.storageCloudService
        .query({ filter: 'professionista-is-null' })
        .pipe(
          map((res: HttpResponse<IStorageCloudFrontend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStorageCloudFrontend[]) => {
          if (!professionista.storageCloudId) {
            this.storageclouds = resBody;
          } else {
            this.storageCloudService
              .find(professionista.storageCloudId)
              .pipe(
                map((subRes: HttpResponse<IStorageCloudFrontend>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IStorageCloudFrontend[]) => (this.storageclouds = concatRes));
          }
        });
    });
  }

  updateForm(professionista: IProfessionistaFrontend): void {
    this.editForm.patchValue({
      id: professionista.id,
      idProfessionista: professionista.idProfessionista,
      nome: professionista.nome,
      cognome: professionista.cognome,
      tipologia: professionista.tipologia,
      codiceFiscale: professionista.codiceFiscale,
      pIva: professionista.pIva,
      studioAssociato: professionista.studioAssociato,
      idLicenza: professionista.idLicenza,
      storageCloudId: professionista.storageCloudId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const professionista = this.createFromForm();
    if (professionista.id !== undefined) {
      this.subscribeToSaveResponse(this.professionistaService.update(professionista));
    } else {
      this.subscribeToSaveResponse(this.professionistaService.create(professionista));
    }
  }

  private createFromForm(): IProfessionistaFrontend {
    return {
      ...new ProfessionistaFrontend(),
      id: this.editForm.get(['id'])!.value,
      idProfessionista: this.editForm.get(['idProfessionista'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cognome: this.editForm.get(['cognome'])!.value,
      tipologia: this.editForm.get(['tipologia'])!.value,
      codiceFiscale: this.editForm.get(['codiceFiscale'])!.value,
      pIva: this.editForm.get(['pIva'])!.value,
      studioAssociato: this.editForm.get(['studioAssociato'])!.value,
      idLicenza: this.editForm.get(['idLicenza'])!.value,
      storageCloudId: this.editForm.get(['storageCloudId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfessionistaFrontend>>): void {
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

  trackById(index: number, item: IStorageCloudFrontend): any {
    return item.id;
  }
}
