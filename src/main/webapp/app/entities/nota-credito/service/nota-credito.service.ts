import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotaCredito, NewNotaCredito } from '../nota-credito.model';

export type PartialUpdateNotaCredito = Partial<INotaCredito> & Pick<INotaCredito, 'id'>;

type RestOf<T extends INotaCredito | NewNotaCredito> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestNotaCredito = RestOf<INotaCredito>;

export type NewRestNotaCredito = RestOf<NewNotaCredito>;

export type PartialUpdateRestNotaCredito = RestOf<PartialUpdateNotaCredito>;

export type EntityResponseType = HttpResponse<INotaCredito>;
export type EntityArrayResponseType = HttpResponse<INotaCredito[]>;

@Injectable({ providedIn: 'root' })
export class NotaCreditoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nota-creditos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(notaCredito: NewNotaCredito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notaCredito);
    return this.http
      .post<RestNotaCredito>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(notaCredito: INotaCredito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notaCredito);
    return this.http
      .put<RestNotaCredito>(`${this.resourceUrl}/${this.getNotaCreditoIdentifier(notaCredito)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(notaCredito: PartialUpdateNotaCredito): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notaCredito);
    return this.http
      .patch<RestNotaCredito>(`${this.resourceUrl}/${this.getNotaCreditoIdentifier(notaCredito)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNotaCredito>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNotaCredito[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotaCreditoIdentifier(notaCredito: Pick<INotaCredito, 'id'>): number {
    return notaCredito.id;
  }

  compareNotaCredito(o1: Pick<INotaCredito, 'id'> | null, o2: Pick<INotaCredito, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotaCreditoIdentifier(o1) === this.getNotaCreditoIdentifier(o2) : o1 === o2;
  }

  addNotaCreditoToCollectionIfMissing<Type extends Pick<INotaCredito, 'id'>>(
    notaCreditoCollection: Type[],
    ...notaCreditosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notaCreditos: Type[] = notaCreditosToCheck.filter(isPresent);
    if (notaCreditos.length > 0) {
      const notaCreditoCollectionIdentifiers = notaCreditoCollection.map(
        notaCreditoItem => this.getNotaCreditoIdentifier(notaCreditoItem)!,
      );
      const notaCreditosToAdd = notaCreditos.filter(notaCreditoItem => {
        const notaCreditoIdentifier = this.getNotaCreditoIdentifier(notaCreditoItem);
        if (notaCreditoCollectionIdentifiers.includes(notaCreditoIdentifier)) {
          return false;
        }
        notaCreditoCollectionIdentifiers.push(notaCreditoIdentifier);
        return true;
      });
      return [...notaCreditosToAdd, ...notaCreditoCollection];
    }
    return notaCreditoCollection;
  }

  protected convertDateFromClient<T extends INotaCredito | NewNotaCredito | PartialUpdateNotaCredito>(notaCredito: T): RestOf<T> {
    return {
      ...notaCredito,
      fecha: notaCredito.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restNotaCredito: RestNotaCredito): INotaCredito {
    return {
      ...restNotaCredito,
      fecha: restNotaCredito.fecha ? dayjs(restNotaCredito.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNotaCredito>): HttpResponse<INotaCredito> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNotaCredito[]>): HttpResponse<INotaCredito[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
