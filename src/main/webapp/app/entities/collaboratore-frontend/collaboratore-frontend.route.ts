import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICollaboratoreFrontend, CollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';
import { CollaboratoreFrontendService } from './collaboratore-frontend.service';
import { CollaboratoreFrontendComponent } from './collaboratore-frontend.component';
import { CollaboratoreFrontendDetailComponent } from './collaboratore-frontend-detail.component';
import { CollaboratoreFrontendUpdateComponent } from './collaboratore-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class CollaboratoreFrontendResolve implements Resolve<ICollaboratoreFrontend> {
  constructor(private service: CollaboratoreFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollaboratoreFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collaboratore: HttpResponse<CollaboratoreFrontend>) => {
          if (collaboratore.body) {
            return of(collaboratore.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CollaboratoreFrontend());
  }
}

export const collaboratoreRoute: Routes = [
  {
    path: '',
    component: CollaboratoreFrontendComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'eoloTestApp.collaboratore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollaboratoreFrontendDetailComponent,
    resolve: {
      collaboratore: CollaboratoreFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.collaboratore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollaboratoreFrontendUpdateComponent,
    resolve: {
      collaboratore: CollaboratoreFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.collaboratore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollaboratoreFrontendUpdateComponent,
    resolve: {
      collaboratore: CollaboratoreFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.collaboratore.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
