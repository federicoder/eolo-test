import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClienteFrontend } from 'app/shared/model/cliente-frontend.model';
import { ClienteFrontendService } from './cliente-frontend.service';

@Component({
  templateUrl: './cliente-frontend-delete-dialog.component.html'
})
export class ClienteFrontendDeleteDialogComponent {
  cliente?: IClienteFrontend;

  constructor(
    protected clienteService: ClienteFrontendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('clienteListModification');
      this.activeModal.close();
    });
  }
}
