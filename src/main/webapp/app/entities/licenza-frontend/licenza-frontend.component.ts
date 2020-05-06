import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILicenzaFrontend } from 'app/shared/model/licenza-frontend.model';
import { LicenzaFrontendService } from './licenza-frontend.service';
import { LicenzaFrontendDeleteDialogComponent } from './licenza-frontend-delete-dialog.component';

@Component({
  selector: 'jhi-licenza-frontend',
  templateUrl: './licenza-frontend.component.html'
})
export class LicenzaFrontendComponent implements OnInit, OnDestroy {
  licenzas?: ILicenzaFrontend[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected licenzaService: LicenzaFrontendService,
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
      this.licenzaService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILicenzaFrontend[]>) => (this.licenzas = res.body || []));
      return;
    }

    this.licenzaService.query().subscribe((res: HttpResponse<ILicenzaFrontend[]>) => (this.licenzas = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLicenzas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILicenzaFrontend): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLicenzas(): void {
    this.eventSubscriber = this.eventManager.subscribe('licenzaListModification', () => this.loadAll());
  }

  delete(licenza: ILicenzaFrontend): void {
    const modalRef = this.modalService.open(LicenzaFrontendDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.licenza = licenza;
  }
}
