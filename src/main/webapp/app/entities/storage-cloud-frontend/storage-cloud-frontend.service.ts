import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IStorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';

type EntityResponseType = HttpResponse<IStorageCloudFrontend>;
type EntityArrayResponseType = HttpResponse<IStorageCloudFrontend[]>;

@Injectable({ providedIn: 'root' })
export class StorageCloudFrontendService {
  public resourceUrl = SERVER_API_URL + 'api/storage-clouds';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/storage-clouds';

  constructor(protected http: HttpClient) {}

  create(storageCloud: IStorageCloudFrontend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(storageCloud);
    return this.http
      .post<IStorageCloudFrontend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(storageCloud: IStorageCloudFrontend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(storageCloud);
    return this.http
      .put<IStorageCloudFrontend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStorageCloudFrontend>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStorageCloudFrontend[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStorageCloudFrontend[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(storageCloud: IStorageCloudFrontend): IStorageCloudFrontend {
    const copy: IStorageCloudFrontend = Object.assign({}, storageCloud, {
      dataCessione:
        storageCloud.dataCessione && storageCloud.dataCessione.isValid() ? storageCloud.dataCessione.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCessione = res.body.dataCessione ? moment(res.body.dataCessione) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((storageCloud: IStorageCloudFrontend) => {
        storageCloud.dataCessione = storageCloud.dataCessione ? moment(storageCloud.dataCessione) : undefined;
      });
    }
    return res;
  }
}
