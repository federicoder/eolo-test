import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'professionista-frontend',
        loadChildren: () =>
          import('./professionista-frontend/professionista-frontend.module').then(m => m.EoloTestProfessionistaFrontendModule)
      },
      {
        path: 'licenza-frontend',
        loadChildren: () => import('./licenza-frontend/licenza-frontend.module').then(m => m.EoloTestLicenzaFrontendModule)
      },
      {
        path: 'pratica-frontend',
        loadChildren: () => import('./pratica-frontend/pratica-frontend.module').then(m => m.EoloTestPraticaFrontendModule)
      },
      {
        path: 'collaboratore-frontend',
        loadChildren: () =>
          import('./collaboratore-frontend/collaboratore-frontend.module').then(m => m.EoloTestCollaboratoreFrontendModule)
      },
      {
        path: 'cliente-frontend',
        loadChildren: () => import('./cliente-frontend/cliente-frontend.module').then(m => m.EoloTestClienteFrontendModule)
      },
      {
        path: 'invito-frontend',
        loadChildren: () => import('./invito-frontend/invito-frontend.module').then(m => m.EoloTestInvitoFrontendModule)
      },
      {
        path: 'storage-cloud-frontend',
        loadChildren: () => import('./storage-cloud-frontend/storage-cloud-frontend.module').then(m => m.EoloTestStorageCloudFrontendModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EoloTestEntityModule {}
