import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPraticaFrontend, PraticaFrontend } from 'app/shared/model/pratica-frontend.model';
import { PraticaFrontendService } from './pratica-frontend.service';
import { PraticaFrontendComponent } from './pratica-frontend.component';
import { PraticaFrontendDetailComponent } from './pratica-frontend-detail.component';
import { PraticaFrontendUpdateComponent } from './pratica-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class PraticaFrontendResolve implements Resolve<IPraticaFrontend> {
  constructor(private service: PraticaFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPraticaFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pratica: HttpResponse<PraticaFrontend>) => {
          if (pratica.body) {
            return of(pratica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PraticaFrontend());
  }
}

export const praticaRoute: Routes = [
  {
    path: '',
    component: PraticaFrontendComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.pratica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PraticaFrontendDetailComponent,
    resolve: {
      pratica: PraticaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.pratica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PraticaFrontendUpdateComponent,
    resolve: {
      pratica: PraticaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.pratica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PraticaFrontendUpdateComponent,
    resolve: {
      pratica: PraticaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.pratica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
