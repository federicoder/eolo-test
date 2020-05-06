import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';
import { StorageCloudFrontendService } from './storage-cloud-frontend.service';

@Component({
  templateUrl: './storage-cloud-frontend-delete-dialog.component.html'
})
export class StorageCloudFrontendDeleteDialogComponent {
  storageCloud?: IStorageCloudFrontend;

  constructor(
    protected storageCloudService: StorageCloudFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.storageCloudService.delete(id).subscribe(() => {
      this.eventManager.broadcast('storageCloudListModification');
      this.activeModal.close();
    });
  }
}
