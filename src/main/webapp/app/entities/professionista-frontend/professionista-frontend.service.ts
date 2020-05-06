import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';

type EntityResponseType = HttpResponse<IProfessionistaFrontend>;
type EntityArrayResponseType = HttpResponse<IProfessionistaFrontend[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionistaFrontendService {
  public resourceUrl = SERVER_API_URL + 'api/professionistas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/professionistas';

  constructor(protected http: HttpClient) {}

  create(professionista: IProfessionistaFrontend): Observable<EntityResponseType> {
    return this.http.post<IProfessionistaFrontend>(this.resourceUrl, professionista, { observe: 'response' });
  }

  update(professionista: IProfessionistaFrontend): Observable<EntityResponseType> {
    return this.http.put<IProfessionistaFrontend>(this.resourceUrl, professionista, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfessionistaFrontend>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfessionistaFrontend[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfessionistaFrontend[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
