import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILicenzaFrontend, LicenzaFrontend } from 'app/shared/model/licenza-frontend.model';
import { LicenzaFrontendService } from './licenza-frontend.service';
import { LicenzaFrontendComponent } from './licenza-frontend.component';
import { LicenzaFrontendDetailComponent } from './licenza-frontend-detail.component';
import { LicenzaFrontendUpdateComponent } from './licenza-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class LicenzaFrontendResolve implements Resolve<ILicenzaFrontend> {
  constructor(private service: LicenzaFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicenzaFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((licenza: HttpResponse<LicenzaFrontend>) => {
          if (licenza.body) {
            return of(licenza.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LicenzaFrontend());
  }
}

export const licenzaRoute: Routes = [
  {
    path: '',
    component: LicenzaFrontendComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.licenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LicenzaFrontendDetailComponent,
    resolve: {
      licenza: LicenzaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.licenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LicenzaFrontendUpdateComponent,
    resolve: {
      licenza: LicenzaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.licenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LicenzaFrontendUpdateComponent,
    resolve: {
      licenza: LicenzaFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.licenza.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
