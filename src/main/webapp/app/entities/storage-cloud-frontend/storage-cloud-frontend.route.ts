import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStorageCloudFrontend, StorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';
import { StorageCloudFrontendService } from './storage-cloud-frontend.service';
import { StorageCloudFrontendComponent } from './storage-cloud-frontend.component';
import { StorageCloudFrontendDetailComponent } from './storage-cloud-frontend-detail.component';
import { StorageCloudFrontendUpdateComponent } from './storage-cloud-frontend-update.component';

@Injectable({ providedIn: 'root' })
export class StorageCloudFrontendResolve implements Resolve<IStorageCloudFrontend> {
  constructor(private service: StorageCloudFrontendService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStorageCloudFrontend> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((storageCloud: HttpResponse<StorageCloudFrontend>) => {
          if (storageCloud.body) {
            return of(storageCloud.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StorageCloudFrontend());
  }
}

export const storageCloudRoute: Routes = [
  {
    path: '',
    component: StorageCloudFrontendComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.storageCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StorageCloudFrontendDetailComponent,
    resolve: {
      storageCloud: StorageCloudFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.storageCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StorageCloudFrontendUpdateComponent,
    resolve: {
      storageCloud: StorageCloudFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.storageCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StorageCloudFrontendUpdateComponent,
    resolve: {
      storageCloud: StorageCloudFrontendResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'eoloTestApp.storageCloud.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
