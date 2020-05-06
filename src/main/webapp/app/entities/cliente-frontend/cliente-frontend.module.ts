import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EoloTestSharedModule } from 'app/shared/shared.module';
import { ClienteFrontendComponent } from './cliente-frontend.component';
import { ClienteFrontendDetailComponent } from './cliente-frontend-detail.component';
import { ClienteFrontendUpdateComponent } from './cliente-frontend-update.component';
import { ClienteFrontendDeleteDialogComponent } from './cliente-frontend-delete-dialog.component';
import { clienteRoute } from './cliente-frontend.route';

@NgModule({
  imports: [EoloTestSharedModule, RouterModule.forChild(clienteRoute)],
  declarations: [
    ClienteFrontendComponent,
    ClienteFrontendDetailComponent,
    ClienteFrontendUpdateComponent,
    ClienteFrontendDeleteDialogComponent
  ],
  entryComponents: [ClienteFrontendDeleteDialogComponent]
})
export class EoloTestClienteFrontendModule {}
