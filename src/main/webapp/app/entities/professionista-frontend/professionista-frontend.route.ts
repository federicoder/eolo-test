import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProfessionistaFrontend, ProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';
import { ProfessionistaFrontendService } from './professionista-frontend.service';
import { ProfessionistaFrontendComponent } from './professionista-frontend.component';
import { ProfessionistaFrontendDetailComponent } from './professionista-frontend-detail.component';
import { ProfessionistaFrontendUpdateComponent } from './professionista-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class ProfessionistaFrontendResolve implements Resolve<IProfessionistaFrontend> {
  constructor(private service: ProfessionistaFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfessionistaFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((professionista: HttpResponse<ProfessionistaFrontend>) => {
          if (professionista.body) {
            return of(professionista.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProfessionistaFrontend());
  }
}

export const professionistaRoute: Routes = [
  {
    path: '',
    component: ProfessionistaFrontendComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'eoloTestApp.professionista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionistaFrontendDetailComponent,
    resolve: {
      professionista: ProfessionistaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.professionista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionistaFrontendUpdateComponent,
    resolve: {
      professionista: ProfessionistaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.professionista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionistaFrontendUpdateComponent,
    resolve: {
      professionista: ProfessionistaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.professionista.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
