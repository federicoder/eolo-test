import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvitoFrontend, InvitoFrontend } from 'app/shared/model/invito-frontend.model';
import { InvitoFrontendService } from './invito-frontend.service';
import { InvitoFrontendComponent } from './invito-frontend.component';
import { InvitoFrontendDetailComponent } from './invito-frontend-detail.component';
import { InvitoFrontendUpdateComponent } from './invito-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class InvitoFrontendResolve implements Resolve<IInvitoFrontend> {
  constructor(private service: InvitoFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvitoFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invito: HttpResponse<InvitoFrontend>) => {
          if (invito.body) {
            return of(invito.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvitoFrontend());
  }
}

export const invitoRoute: Routes = [
  {
    path: '',
    component: InvitoFrontendComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.invito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvitoFrontendDetailComponent,
    resolve: {
      invito: InvitoFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.invito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvitoFrontendUpdateComponent,
    resolve: {
      invito: InvitoFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.invito.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvitoFrontendUpdateComponent,
    resolve: {
      invito: InvitoFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.invito.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
