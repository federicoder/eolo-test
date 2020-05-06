import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { CollaboratoreFrontendComponent } from './collaboratore-frontend.component';
import { CollaboratoreFrontendDetailComponent } from './collaboratore-frontend-detail.component';
import { CollaboratoreFrontendUpdateComponent } from './collaboratore-frontend-update.component';
import { CollaboratoreFrontendDeleteDialogComponent } from './collaboratore-frontend-delete-dialog.component';
import { collaboratoreRoute } from './collaboratore-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(collaboratoreRoute)],
  declarations: [
    CollaboratoreFrontendComponent,
    CollaboratoreFrontendDetailComponent,
    CollaboratoreFrontendUpdateComponent,
    CollaboratoreFrontendDeleteDialogComponent
  ],
  entryComponents: [CollaboratoreFrontendDeleteDialogComponent]
})
export class EoloTestCollaboratoreFrontendModule {}
