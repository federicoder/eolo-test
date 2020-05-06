import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvitoFrontend } from 'app/shared/model/invito-frontend.model';
import { InvitoFrontendService } from './invito-frontend.service';

@Component({
  templateUrl: './invito-frontend-delete-dialog.component.html'
})
export class InvitoFrontendDeleteDialogComponent {
  invito?: IInvitoFrontend;

  constructor(
    protected invitoService: InvitoFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invitoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invitoListModification');
      this.activeModal.close();
    });
  }
}
