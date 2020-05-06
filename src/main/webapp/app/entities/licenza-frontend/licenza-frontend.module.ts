import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { LicenzaFrontendComponent } from './licenza-frontend.component';
import { LicenzaFrontendDetailComponent } from './licenza-frontend-detail.component';
import { LicenzaFrontendUpdateComponent } from './licenza-frontend-update.component';
import { LicenzaFrontendDeleteDialogComponent } from './licenza-frontend-delete-dialog.component';
import { licenzaRoute } from './licenza-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(licenzaRoute)],
  declarations: [
    LicenzaFrontendComponent,
    LicenzaFrontendDetailComponent,
    LicenzaFrontendUpdateComponent,
    LicenzaFrontendDeleteDialogComponent
  ],
  entryComponents: [LicenzaFrontendDeleteDialogComponent]
})
export class EoloTestLicenzaFrontendModule {}
