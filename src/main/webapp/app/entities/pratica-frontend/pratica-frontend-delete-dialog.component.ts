import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPraticaFrontend } from 'app/shared/model/pratica-frontend.model';
import { PraticaFrontendService } from './pratica-frontend.service';

@Component({
  templateUrl: './pratica-frontend-delete-dialog.component.html'
})
export class PraticaFrontendDeleteDialogComponent {
  pratica?: IPraticaFrontend;

  constructor(
    protected praticaService: PraticaFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.praticaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('praticaListModification');
      this.activeModal.close();
    });
  }
}
