import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICursos, NewCursos } from '../cursos.model';

export type PartialUpdateCursos = Partial<ICursos> & Pick<ICursos, 'id'>;

export type EntityResponseType = HttpResponse<ICursos>;
export type EntityArrayResponseType = HttpResponse<ICursos[]>;

@Injectable({ providedIn: 'root' })
export class CursosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cursos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cursos: NewCursos): Observable<EntityResponseType> {
    return this.http.post<ICursos>(this.resourceUrl, cursos, { observe: 'response' });
  }

  update(cursos: ICursos): Observable<EntityResponseType> {
    return this.http.put<ICursos>(`${this.resourceUrl}/${this.getCursosIdentifier(cursos)}`, cursos, { observe: 'response' });
  }

  partialUpdate(cursos: PartialUpdateCursos): Observable<EntityResponseType> {
    return this.http.patch<ICursos>(`${this.resourceUrl}/${this.getCursosIdentifier(cursos)}`, cursos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICursos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICursos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCursosIdentifier(cursos: Pick<ICursos, 'id'>): number {
    return cursos.id;
  }

  compareCursos(o1: Pick<ICursos, 'id'> | null, o2: Pick<ICursos, 'id'> | null): boolean {
    return o1 && o2 ? this.getCursosIdentifier(o1) === this.getCursosIdentifier(o2) : o1 === o2;
  }

  addCursosToCollectionIfMissing<Type extends Pick<ICursos, 'id'>>(
    cursosCollection: Type[],
    ...cursosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cursos: Type[] = cursosToCheck.filter(isPresent);
    if (cursos.length > 0) {
      const cursosCollectionIdentifiers = cursosCollection.map(cursosItem => this.getCursosIdentifier(cursosItem)!);
      const cursosToAdd = cursos.filter(cursosItem => {
        const cursosIdentifier = this.getCursosIdentifier(cursosItem);
        if (cursosCollectionIdentifiers.includes(cursosIdentifier)) {
          return false;
        }
        cursosCollectionIdentifiers.push(cursosIdentifier);
        return true;
      });
      return [...cursosToAdd, ...cursosCollection];
    }
    return cursosCollection;
  }
}
