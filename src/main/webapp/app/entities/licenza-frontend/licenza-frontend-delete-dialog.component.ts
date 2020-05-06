import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILicenzaFrontend } from 'app/shared/model/licenza-frontend.model';
import { LicenzaFrontendService } from './licenza-frontend.service';

@Component({
  templateUrl: './licenza-frontend-delete-dialog.component.html'
})
export class LicenzaFrontendDeleteDialogComponent {
  licenza?: ILicenzaFrontend;

  constructor(
    protected licenzaService: LicenzaFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.licenzaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('licenzaListModification');
      this.activeModal.close();
    });
  }
}
