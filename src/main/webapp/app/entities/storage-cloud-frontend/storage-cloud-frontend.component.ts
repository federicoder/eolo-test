import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';
import { StorageCloudFrontendService } from './storage-cloud-frontend.service';
import { StorageCloudFrontendDeleteDialogComponent } from './storage-cloud-frontend-delete-dialog.component';

@Component({
  selector: 'jhi-storage-cloud-frontend',
  templateUrl: './storage-cloud-frontend.component.html'
})
export class StorageCloudFrontendComponent implements OnInit, OnDestroy {
  storageClouds?: IStorageCloudFrontend[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected storageCloudService: StorageCloudFrontendService,
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
      this.storageCloudService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IStorageCloudFrontend[]>) => (this.storageClouds = res.body || []));
      return;
    }

    this.storageCloudService.query().subscribe((res: HttpResponse<IStorageCloudFrontend[]>) => (this.storageClouds = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStorageClouds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStorageCloudFrontend): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStorageClouds(): void {
    this.eventSubscriber = this.eventManager.subscribe('storageCloudListModification', () => this.loadAll());
  }

  delete(storageCloud: IStorageCloudFrontend): void {
    const modalRef = this.modalService.open(StorageCloudFrontendDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.storageCloud = storageCloud;
  }
}
