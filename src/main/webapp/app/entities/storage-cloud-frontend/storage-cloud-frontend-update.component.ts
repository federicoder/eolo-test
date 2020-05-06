import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStorageCloudFrontend, StorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';
import { StorageCloudFrontendService } from './storage-cloud-frontend.service';

@Component({
  selector: 'jhi-storage-cloud-frontend-update',
  templateUrl: './storage-cloud-frontend-update.component.html'
})
export class StorageCloudFrontendUpdateComponent implements OnInit {
  isSaving = false;
  dataCessioneDp: any;

  editForm = this.fb.group({
    id: [],
    idUtente: [null, [Validators.required]],
    idLicenza: [null, [Validators.required]],
    pianoBase: [],
    dataCessione: []
  });

  constructor(
    protected storageCloudService: StorageCloudFrontendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storageCloud }) => {
      this.updateForm(storageCloud);
    });
  }

  updateForm(storageCloud: IStorageCloudFrontend): void {
    this.editForm.patchValue({
      id: storageCloud.id,
      idUtente: storageCloud.idUtente,
      idLicenza: storageCloud.idLicenza,
      pianoBase: storageCloud.pianoBase,
      dataCessione: storageCloud.dataCessione
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const storageCloud = this.createFromForm();
    if (storageCloud.id !== undefined) {
      this.subscribeToSaveResponse(this.storageCloudService.update(storageCloud));
    } else {
      this.subscribeToSaveResponse(this.storageCloudService.create(storageCloud));
    }
  }

  private createFromForm(): IStorageCloudFrontend {
    return {
      ...new StorageCloudFrontend(),
      id: this.editForm.get(['id'])!.value,
      idUtente: this.editForm.get(['idUtente'])!.value,
      idLicenza: this.editForm.get(['idLicenza'])!.value,
      pianoBase: this.editForm.get(['pianoBase'])!.value,
      dataCessione: this.editForm.get(['dataCessione'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStorageCloudFrontend>>): void {
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
