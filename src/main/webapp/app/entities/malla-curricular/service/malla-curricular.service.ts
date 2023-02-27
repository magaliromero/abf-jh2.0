import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMallaCurricular, NewMallaCurricular } from '../malla-curricular.model';

export type PartialUpdateMallaCurricular = Partial<IMallaCurricular> & Pick<IMallaCurricular, 'id'>;

export type EntityResponseType = HttpResponse<IMallaCurricular>;
export type EntityArrayResponseType = HttpResponse<IMallaCurricular[]>;

@Injectable({ providedIn: 'root' })
export class MallaCurricularService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/malla-curriculars');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mallaCurricular: NewMallaCurricular): Observable<EntityResponseType> {
    return this.http.post<IMallaCurricular>(this.resourceUrl, mallaCurricular, { observe: 'response' });
  }

  update(mallaCurricular: IMallaCurricular): Observable<EntityResponseType> {
    return this.http.put<IMallaCurricular>(`${this.resourceUrl}/${this.getMallaCurricularIdentifier(mallaCurricular)}`, mallaCurricular, {
      observe: 'response',
    });
  }

  partialUpdate(mallaCurricular: PartialUpdateMallaCurricular): Observable<EntityResponseType> {
    return this.http.patch<IMallaCurricular>(`${this.resourceUrl}/${this.getMallaCurricularIdentifier(mallaCurricular)}`, mallaCurricular, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMallaCurricular>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMallaCurricular[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMallaCurricularIdentifier(mallaCurricular: Pick<IMallaCurricular, 'id'>): number {
    return mallaCurricular.id;
  }

  compareMallaCurricular(o1: Pick<IMallaCurricular, 'id'> | null, o2: Pick<IMallaCurricular, 'id'> | null): boolean {
    return o1 && o2 ? this.getMallaCurricularIdentifier(o1) === this.getMallaCurricularIdentifier(o2) : o1 === o2;
  }

  addMallaCurricularToCollectionIfMissing<Type extends Pick<IMallaCurricular, 'id'>>(
    mallaCurricularCollection: Type[],
    ...mallaCurricularsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mallaCurriculars: Type[] = mallaCurricularsToCheck.filter(isPresent);
    if (mallaCurriculars.length > 0) {
      const mallaCurricularCollectionIdentifiers = mallaCurricularCollection.map(
        mallaCurricularItem => this.getMallaCurricularIdentifier(mallaCurricularItem)!
      );
      const mallaCurricularsToAdd = mallaCurriculars.filter(mallaCurricularItem => {
        const mallaCurricularIdentifier = this.getMallaCurricularIdentifier(mallaCurricularItem);
        if (mallaCurricularCollectionIdentifiers.includes(mallaCurricularIdentifier)) {
          return false;
        }
        mallaCurricularCollectionIdentifiers.push(mallaCurricularIdentifier);
        return true;
      });
      return [...mallaCurricularsToAdd, ...mallaCurricularCollection];
    }
    return mallaCurricularCollection;
  }
}
