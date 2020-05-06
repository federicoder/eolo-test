import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { PraticaFrontendComponent } from './pratica-frontend.component';
import { PraticaFrontendDetailComponent } from './pratica-frontend-detail.component';
import { PraticaFrontendUpdateComponent } from './pratica-frontend-update.component';
import { PraticaFrontendDeleteDialogComponent } from './pratica-frontend-delete-dialog.component';
import { praticaRoute } from './pratica-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(praticaRoute)],
  declarations: [
    PraticaFrontendComponent,
    PraticaFrontendDetailComponent,
    PraticaFrontendUpdateComponent,
    PraticaFrontendDeleteDialogComponent
  ],
  entryComponents: [PraticaFrontendDeleteDialogComponent]
})
export class EoloTestPraticaFrontendModule {}
