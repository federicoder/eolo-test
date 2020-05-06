import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPraticaFrontend } from 'app/shared/model/pratica-frontend.model';

type EntityResponseType = HttpResponse<IPraticaFrontend>;
type EntityArrayResponseType = HttpResponse<IPraticaFrontend[]>;

@Injectable({ providedIn: 'root' })
export class PraticaFrontendService {
  public resourceUrl = SERVER_API_URL + 'api/praticas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/praticas';

  constructor(protected http: HttpClient) {}

  create(pratica: IPraticaFrontend): Observable<EntityResponseType> {
    return this.http.post<IPraticaFrontend>(this.resourceUrl, pratica, { observe: 'response' });
  }

  update(pratica: IPraticaFrontend): Observable<EntityResponseType> {
    return this.http.put<IPraticaFrontend>(this.resourceUrl, pratica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPraticaFrontend>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPraticaFrontend[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPraticaFrontend[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
