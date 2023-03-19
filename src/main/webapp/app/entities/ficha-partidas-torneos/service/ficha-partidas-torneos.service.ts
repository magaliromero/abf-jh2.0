import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFichaPartidasTorneos, NewFichaPartidasTorneos } from '../ficha-partidas-torneos.model';

export type PartialUpdateFichaPartidasTorneos = Partial<IFichaPartidasTorneos> & Pick<IFichaPartidasTorneos, 'id'>;

export type EntityResponseType = HttpResponse<IFichaPartidasTorneos>;
export type EntityArrayResponseType = HttpResponse<IFichaPartidasTorneos[]>;

@Injectable({ providedIn: 'root' })
export class FichaPartidasTorneosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ficha-partidas-torneos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fichaPartidasTorneos: NewFichaPartidasTorneos): Observable<EntityResponseType> {
    return this.http.post<IFichaPartidasTorneos>(this.resourceUrl, fichaPartidasTorneos, { observe: 'response' });
  }

  update(fichaPartidasTorneos: IFichaPartidasTorneos): Observable<EntityResponseType> {
    return this.http.put<IFichaPartidasTorneos>(
      `${this.resourceUrl}/${this.getFichaPartidasTorneosIdentifier(fichaPartidasTorneos)}`,
      fichaPartidasTorneos,
      { observe: 'response' }
    );
  }

  partialUpdate(fichaPartidasTorneos: PartialUpdateFichaPartidasTorneos): Observable<EntityResponseType> {
    return this.http.patch<IFichaPartidasTorneos>(
      `${this.resourceUrl}/${this.getFichaPartidasTorneosIdentifier(fichaPartidasTorneos)}`,
      fichaPartidasTorneos,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFichaPartidasTorneos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFichaPartidasTorneos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFichaPartidasTorneosIdentifier(fichaPartidasTorneos: Pick<IFichaPartidasTorneos, 'id'>): number {
    return fichaPartidasTorneos.id;
  }

  compareFichaPartidasTorneos(o1: Pick<IFichaPartidasTorneos, 'id'> | null, o2: Pick<IFichaPartidasTorneos, 'id'> | null): boolean {
    return o1 && o2 ? this.getFichaPartidasTorneosIdentifier(o1) === this.getFichaPartidasTorneosIdentifier(o2) : o1 === o2;
  }

  addFichaPartidasTorneosToCollectionIfMissing<Type extends Pick<IFichaPartidasTorneos, 'id'>>(
    fichaPartidasTorneosCollection: Type[],
    ...fichaPartidasTorneosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fichaPartidasTorneos: Type[] = fichaPartidasTorneosToCheck.filter(isPresent);
    if (fichaPartidasTorneos.length > 0) {
      const fichaPartidasTorneosCollectionIdentifiers = fichaPartidasTorneosCollection.map(
        fichaPartidasTorneosItem => this.getFichaPartidasTorneosIdentifier(fichaPartidasTorneosItem)!
      );
      const fichaPartidasTorneosToAdd = fichaPartidasTorneos.filter(fichaPartidasTorneosItem => {
        const fichaPartidasTorneosIdentifier = this.getFichaPartidasTorneosIdentifier(fichaPartidasTorneosItem);
        if (fichaPartidasTorneosCollectionIdentifiers.includes(fichaPartidasTorneosIdentifier)) {
          return false;
        }
        fichaPartidasTorneosCollectionIdentifiers.push(fichaPartidasTorneosIdentifier);
        return true;
      });
      return [...fichaPartidasTorneosToAdd, ...fichaPartidasTorneosCollection];
    }
    return fichaPartidasTorneosCollection;
  }
}
