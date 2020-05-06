import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILicenzaFrontend } from 'app/shared/model/licenza-frontend.model';

type EntityResponseType = HttpResponse<ILicenzaFrontend>;
type EntityArrayResponseType = HttpResponse<ILicenzaFrontend[]>;

@Injectable({ providedIn: 'root' })
export class LicenzaFrontendService {
  public resourceUrl = SERVER_API_URL + 'api/licenzas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/licenzas';

  constructor(protected http: HttpClient) {}

  create(licenza: ILicenzaFrontend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licenza);
    return this.http
      .post<ILicenzaFrontend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(licenza: ILicenzaFrontend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(licenza);
    return this.http
      .put<ILicenzaFrontend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILicenzaFrontend>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILicenzaFrontend[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILicenzaFrontend[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(licenza: ILicenzaFrontend): ILicenzaFrontend {
    const copy: ILicenzaFrontend = Object.assign({}, licenza, {
      dataScadenza: licenza.dataScadenza && licenza.dataScadenza.isValid() ? licenza.dataScadenza.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataScadenza = res.body.dataScadenza ? moment(res.body.dataScadenza) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((licenza: ILicenzaFrontend) => {
        licenza.dataScadenza = licenza.dataScadenza ? moment(licenza.dataScadenza) : undefined;
      });
    }
    return res;
  }
}
