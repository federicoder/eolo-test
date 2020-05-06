import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IClienteFrontend } from 'app/shared/model/cliente-frontend.model';

type EntityResponseType = HttpResponse<IClienteFrontend>;
type EntityArrayResponseType = HttpResponse<IClienteFrontend[]>;

@Injectable({ providedIn: 'root' })
export class ClienteFrontendService {
  public resourceUrl = SERVER_API_URL + 'api/clientes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/clientes';

  constructor(protected http: HttpClient) {}

  create(cliente: IClienteFrontend): Observable<EntityResponseType> {
    return this.http.post<IClienteFrontend>(this.resourceUrl, cliente, { observe: 'response' });
  }

  update(cliente: IClienteFrontend): Observable<EntityResponseType> {
    return this.http.put<IClienteFrontend>(this.resourceUrl, cliente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClienteFrontend>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClienteFrontend[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClienteFrontend[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
