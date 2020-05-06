import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';

type EntityResponseType = HttpResponse<ICollaboratoreFrontend>;
type EntityArrayResponseType = HttpResponse<ICollaboratoreFrontend[]>;

@Injectable({ providedIn: 'root' })
export class CollaboratoreFrontendService {
  public resourceUrl = SERVER_API_URL + 'api/collaboratores';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/collaboratores';

  constructor(protected http: HttpClient) {}

  create(collaboratore: ICollaboratoreFrontend): Observable<EntityResponseType> {
    return this.http.post<ICollaboratoreFrontend>(this.resourceUrl, collaboratore, { observe: 'response' });
  }

  update(collaboratore: ICollaboratoreFrontend): Observable<EntityResponseType> {
    return this.http.put<ICollaboratoreFrontend>(this.resourceUrl, collaboratore, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICollaboratoreFrontend>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollaboratoreFrontend[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollaboratoreFrontend[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
