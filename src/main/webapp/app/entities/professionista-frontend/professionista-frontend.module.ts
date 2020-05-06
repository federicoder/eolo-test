import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { ProfessionistaFrontendComponent } from './professionista-frontend.component';
import { ProfessionistaFrontendDetailComponent } from './professionista-frontend-detail.component';
import { ProfessionistaFrontendUpdateComponent } from './professionista-frontend-update.component';
import { ProfessionistaFrontendDeleteDialogComponent } from './professionista-frontend-delete-dialog.component';
import { professionistaRoute } from './professionista-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(professionistaRoute)],
  declarations: [
    ProfessionistaFrontendComponent,
    ProfessionistaFrontendDetailComponent,
    ProfessionistaFrontendUpdateComponent,
    ProfessionistaFrontendDeleteDialogComponent
  ],
  entryComponents: [ProfessionistaFrontendDeleteDialogComponent]
})
export class EoloTestProfessionistaFrontendModule {}
