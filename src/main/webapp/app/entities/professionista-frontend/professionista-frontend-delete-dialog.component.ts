import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';
import { ProfessionistaFrontendService } from './professionista-frontend.service';

@Component({
  templateUrl: './professionista-frontend-delete-dialog.component.html'
})
export class ProfessionistaFrontendDeleteDialogComponent {
  professionista?: IProfessionistaFrontend;

  constructor(
    protected professionistaService: ProfessionistaFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.professionistaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('professionistaListModification');
      this.activeModal.close();
    });
  }
}
