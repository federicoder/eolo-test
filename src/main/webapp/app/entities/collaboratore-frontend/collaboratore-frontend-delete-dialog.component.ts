import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';
import { CollaboratoreFrontendService } from './collaboratore-frontend.service';

@Component({
  templateUrl: './collaboratore-frontend-delete-dialog.component.html'
})
export class CollaboratoreFrontendDeleteDialogComponent {
  collaboratore?: ICollaboratoreFrontend;

  constructor(
    protected collaboratoreService: CollaboratoreFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collaboratoreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collaboratoreListModification');
      this.activeModal.close();
    });
  }
}
