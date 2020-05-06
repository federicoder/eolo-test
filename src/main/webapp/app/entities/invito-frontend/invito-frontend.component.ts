import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvitoFrontend } from 'app/shared/model/invito-frontend.model';
import { InvitoFrontendService } from './invito-frontend.service';
import { InvitoFrontendDeleteDialogComponent } from './invito-frontend-delete-dialog.component';

@Component({
  selector: 'jhi-invito-frontend',
  templateUrl: './invito-frontend.component.html'
})
export class InvitoFrontendComponent implements OnInit, OnDestroy {
  invitos?: IInvitoFrontend[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected invitoService: InvitoFrontendService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.invitoService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IInvitoFrontend[]>) => (this.invitos = res.body || []));
      return;
    }

    this.invitoService.query().subscribe((res: HttpResponse<IInvitoFrontend[]>) => (this.invitos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvitos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvitoFrontend): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvitos(): void {
    this.eventSubscriber = this.eventManager.subscribe('invitoListModification', () => this.loadAll());
  }

  delete(invito: IInvitoFrontend): void {
    const modalRef = this.modalService.open(InvitoFrontendDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invito = invito;
  }
}
