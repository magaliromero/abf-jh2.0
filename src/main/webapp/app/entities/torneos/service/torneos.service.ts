import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITorneos, NewTorneos } from '../torneos.model';

export type PartialUpdateTorneos = Partial<ITorneos> & Pick<ITorneos, 'id'>;

type RestOf<T extends ITorneos | NewTorneos> = Omit<T, 'fechaInicio' | 'fechaFin'> & {
  fechaInicio?: string | null;
  fechaFin?: string | null;
};

export type RestTorneos = RestOf<ITorneos>;

export type NewRestTorneos = RestOf<NewTorneos>;

export type PartialUpdateRestTorneos = RestOf<PartialUpdateTorneos>;

export type EntityResponseType = HttpResponse<ITorneos>;
export type EntityArrayResponseType = HttpResponse<ITorneos[]>;

@Injectable({ providedIn: 'root' })
export class TorneosService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/torneos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(torneos: NewTorneos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(torneos);
    return this.http
      .post<RestTorneos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(torneos: ITorneos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(torneos);
    return this.http
      .put<RestTorneos>(`${this.resourceUrl}/${this.getTorneosIdentifier(torneos)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(torneos: PartialUpdateTorneos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(torneos);
    return this.http
      .patch<RestTorneos>(`${this.resourceUrl}/${this.getTorneosIdentifier(torneos)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTorneos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTorneos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTorneosIdentifier(torneos: Pick<ITorneos, 'id'>): number {
    return torneos.id;
  }

  compareTorneos(o1: Pick<ITorneos, 'id'> | null, o2: Pick<ITorneos, 'id'> | null): boolean {
    return o1 && o2 ? this.getTorneosIdentifier(o1) === this.getTorneosIdentifier(o2) : o1 === o2;
  }

  addTorneosToCollectionIfMissing<Type extends Pick<ITorneos, 'id'>>(
    torneosCollection: Type[],
    ...torneosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const torneos: Type[] = torneosToCheck.filter(isPresent);
    if (torneos.length > 0) {
      const torneosCollectionIdentifiers = torneosCollection.map(torneosItem => this.getTorneosIdentifier(torneosItem)!);
      const torneosToAdd = torneos.filter(torneosItem => {
        const torneosIdentifier = this.getTorneosIdentifier(torneosItem);
        if (torneosCollectionIdentifiers.includes(torneosIdentifier)) {
          return false;
        }
        torneosCollectionIdentifiers.push(torneosIdentifier);
        return true;
      });
      return [...torneosToAdd, ...torneosCollection];
    }
    return torneosCollection;
  }

  protected convertDateFromClient<T extends ITorneos | NewTorneos | PartialUpdateTorneos>(torneos: T): RestOf<T> {
    return {
      ...torneos,
      fechaInicio: torneos.fechaInicio?.format(DATE_FORMAT) ?? null,
      fechaFin: torneos.fechaFin?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTorneos: RestTorneos): ITorneos {
    return {
      ...restTorneos,
      fechaInicio: restTorneos.fechaInicio ? dayjs(restTorneos.fechaInicio) : undefined,
      fechaFin: restTorneos.fechaFin ? dayjs(restTorneos.fechaFin) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTorneos>): HttpResponse<ITorneos> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTorneos[]>): HttpResponse<ITorneos[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
