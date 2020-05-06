import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPraticaFrontend } from 'app/shared/model/pratica-frontend.model';
import { PraticaFrontendService } from './pratica-frontend.service';
import { PraticaFrontendDeleteDialogComponent } from './pratica-frontend-delete-dialog.component';

@Component({
  selector: 'jhi-pratica-frontend',
  templateUrl: './pratica-frontend.component.html'
})
export class PraticaFrontendComponent implements OnInit, OnDestroy {
  praticas?: IPraticaFrontend[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected praticaService: PraticaFrontendService,
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
      this.praticaService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IPraticaFrontend[]>) => (this.praticas = res.body || []));
      return;
    }

    this.praticaService.query().subscribe((res: HttpResponse<IPraticaFrontend[]>) => (this.praticas = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPraticas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPraticaFrontend): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPraticas(): void {
    this.eventSubscriber = this.eventManager.subscribe('praticaListModification', () => this.loadAll());
  }

  delete(pratica: IPraticaFrontend): void {
    const modalRef = this.modalService.open(PraticaFrontendDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pratica = pratica;
  }
}
