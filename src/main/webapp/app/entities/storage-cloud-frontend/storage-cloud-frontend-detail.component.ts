import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';

@Component({
  selector: 'jhi-storage-cloud-frontend-detail',
  templateUrl: './storage-cloud-frontend-detail.component.html'
})
export class StorageCloudFrontendDetailComponent implements OnInit {
  storageCloud: IStorageCloudFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storageCloud }) => (this.storageCloud = storageCloud));
  }

  previousState(): void {
    window.history.back();
  }
}
