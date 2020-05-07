import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILicenzaFrontend, LicenzaFrontend } from 'app/shared/model/licenza-frontend.model';
import { LicenzaFrontendService } from './licenza-frontend.service';
import { IProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';
import { ProfessionistaFrontendService } from 'app/entities/professionista-frontend/professionista-frontend.service';
import { IStorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';
import { StorageCloudFrontendService } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend.service';

type SelectableEntity = IProfessionistaFrontend | IStorageCloudFrontend;

@Component({
  selector: 'jhi-licenza-frontend-update',
  templateUrl: './licenza-frontend-update.component.html'
})
export class LicenzaFrontendUpdateComponent implements OnInit {
  isSaving = false;
  professionistas: IProfessionistaFrontend[] = [];
  storageclouds: IStorageCloudFrontend[] = [];
  dataScadenzaDp: any;

  editForm = this.fb.group({
    id: [],
    idLicenza: [null, [Validators.required]],
    tipologia: [],
    descrizione: [],
    dataScadenza: [],
    professionistaId: [],
    storageCloudId: []
  });

  constructor(
    protected licenzaService: LicenzaFrontendService,
    protected professionistaService: ProfessionistaFrontendService,
    protected storageCloudService: StorageCloudFrontendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licenza }) => {
      this.updateForm(licenza);

      this.professionistaService
        .query({ filter: 'licenza-is-null' })
        .pipe(
          map((res: HttpResponse<IProfessionistaFrontend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProfessionistaFrontend[]) => {
          if (!licenza.professionistaId) {
            this.professionistas = resBody;
          } else {
            this.professionistaService
              .find(licenza.professionistaId)
              .pipe(
                map((subRes: HttpResponse<IProfessionistaFrontend>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProfessionistaFrontend[]) => (this.professionistas = concatRes));
          }
        });

      this.storageCloudService
        .query({ filter: 'licenza-is-null' })
        .pipe(
          map((res: HttpResponse<IStorageCloudFrontend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStorageCloudFrontend[]) => {
          if (!licenza.storageCloudId) {
            this.storageclouds = resBody;
          } else {
            this.storageCloudService
              .find(licenza.storageCloudId)
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

  updateForm(licenza: ILicenzaFrontend): void {
    this.editForm.patchValue({
      id: licenza.id,
      idLicenza: licenza.idLicenza,
      tipologia: licenza.tipologia,
      descrizione: licenza.descrizione,
      dataScadenza: licenza.dataScadenza,
      professionistaId: licenza.professionistaId,
      storageCloudId: licenza.storageCloudId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const licenza = this.createFromForm();
    if (licenza.id !== undefined) {
      this.subscribeToSaveResponse(this.licenzaService.update(licenza));
    } else {
      this.subscribeToSaveResponse(this.licenzaService.create(licenza));
    }
  }

  private createFromForm(): ILicenzaFrontend {
    return {
      ...new LicenzaFrontend(),
      id: this.editForm.get(['id'])!.value,
      idLicenza: this.editForm.get(['idLicenza'])!.value,
      tipologia: this.editForm.get(['tipologia'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
      dataScadenza: this.editForm.get(['dataScadenza'])!.value,
      professionistaId: this.editForm.get(['professionistaId'])!.value,
      storageCloudId: this.editForm.get(['storageCloudId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicenzaFrontend>>): void {
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
