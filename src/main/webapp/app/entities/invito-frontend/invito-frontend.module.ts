import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { InvitoFrontendComponent } from './invito-frontend.component';
import { InvitoFrontendDetailComponent } from './invito-frontend-detail.component';
import { InvitoFrontendUpdateComponent } from './invito-frontend-update.component';
import { InvitoFrontendDeleteDialogComponent } from './invito-frontend-delete-dialog.component';
import { invitoRoute } from './invito-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(invitoRoute)],
  declarations: [
    InvitoFrontendComponent,
    InvitoFrontendDetailComponent,
    InvitoFrontendUpdateComponent,
    InvitoFrontendDeleteDialogComponent
  ],
  entryComponents: [InvitoFrontendDeleteDialogComponent]
})
export class EoloTestInvitoFrontendModule {}
