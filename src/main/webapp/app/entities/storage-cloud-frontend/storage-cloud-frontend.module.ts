import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { StorageCloudFrontendComponent } from './storage-cloud-frontend.component';
import { StorageCloudFrontendDetailComponent } from './storage-cloud-frontend-detail.component';
import { StorageCloudFrontendUpdateComponent } from './storage-cloud-frontend-update.component';
import { StorageCloudFrontendDeleteDialogComponent } from './storage-cloud-frontend-delete-dialog.component';
import { storageCloudRoute } from './storage-cloud-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(storageCloudRoute)],
  declarations: [
    StorageCloudFrontendComponent,
    StorageCloudFrontendDetailComponent,
    StorageCloudFrontendUpdateComponent,
    StorageCloudFrontendDeleteDialogComponent
  ],
  entryComponents: [StorageCloudFrontendDeleteDialogComponent]
})
export class EoloTestStorageCloudFrontendModule {}
