import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClienteFrontend, ClienteFrontend } from 'app/shared/model/cliente-frontend.model';
import { ClienteFrontendService } from './cliente-frontend.service';
import { ClienteFrontendComponent } from './cliente-frontend.component';
import { ClienteFrontendDetailComponent } from './cliente-frontend-detail.component';
import { ClienteFrontendUpdateComponent } from './cliente-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class ClienteFrontendResolve implements Resolve<IClienteFrontend> {
  constructor(private service: ClienteFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClienteFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cliente: HttpResponse<ClienteFrontend>) => {
          if (cliente.body) {
            return of(cliente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClienteFrontend());
  }
}

export const clienteRoute: Routes = [
  {
    path: '',
    component: ClienteFrontendComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'eoloTestApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClienteFrontendDetailComponent,
    resolve: {
      cliente: ClienteFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClienteFrontendUpdateComponent,
    resolve: {
      cliente: ClienteFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClienteFrontendUpdateComponent,
    resolve: {
      cliente: ClienteFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.cliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
