import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRegistroStockMateriales, NewRegistroStockMateriales } from '../registro-stock-materiales.model';

export type PartialUpdateRegistroStockMateriales = Partial<IRegistroStockMateriales> & Pick<IRegistroStockMateriales, 'id'>;

type RestOf<T extends IRegistroStockMateriales | NewRegistroStockMateriales> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestRegistroStockMateriales = RestOf<IRegistroStockMateriales>;

export type NewRestRegistroStockMateriales = RestOf<NewRegistroStockMateriales>;

export type PartialUpdateRestRegistroStockMateriales = RestOf<PartialUpdateRegistroStockMateriales>;

export type EntityResponseType = HttpResponse<IRegistroStockMateriales>;
export type EntityArrayResponseType = HttpResponse<IRegistroStockMateriales[]>;

@Injectable({ providedIn: 'root' })
export class RegistroStockMaterialesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/registro-stock-materiales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(registroStockMateriales: NewRegistroStockMateriales): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroStockMateriales);
    return this.http
      .post<RestRegistroStockMateriales>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(registroStockMateriales: IRegistroStockMateriales): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroStockMateriales);
    return this.http
      .put<RestRegistroStockMateriales>(`${this.resourceUrl}/${this.getRegistroStockMaterialesIdentifier(registroStockMateriales)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(registroStockMateriales: PartialUpdateRegistroStockMateriales): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroStockMateriales);
    return this.http
      .patch<RestRegistroStockMateriales>(
        `${this.resourceUrl}/${this.getRegistroStockMaterialesIdentifier(registroStockMateriales)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRegistroStockMateriales>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRegistroStockMateriales[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRegistroStockMaterialesIdentifier(registroStockMateriales: Pick<IRegistroStockMateriales, 'id'>): number {
    return registroStockMateriales.id;
  }

  compareRegistroStockMateriales(
    o1: Pick<IRegistroStockMateriales, 'id'> | null,
    o2: Pick<IRegistroStockMateriales, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getRegistroStockMaterialesIdentifier(o1) === this.getRegistroStockMaterialesIdentifier(o2) : o1 === o2;
  }

  addRegistroStockMaterialesToCollectionIfMissing<Type extends Pick<IRegistroStockMateriales, 'id'>>(
    registroStockMaterialesCollection: Type[],
    ...registroStockMaterialesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const registroStockMateriales: Type[] = registroStockMaterialesToCheck.filter(isPresent);
    if (registroStockMateriales.length > 0) {
      const registroStockMaterialesCollectionIdentifiers = registroStockMaterialesCollection.map(
        registroStockMaterialesItem => this.getRegistroStockMaterialesIdentifier(registroStockMaterialesItem)!
      );
      const registroStockMaterialesToAdd = registroStockMateriales.filter(registroStockMaterialesItem => {
        const registroStockMaterialesIdentifier = this.getRegistroStockMaterialesIdentifier(registroStockMaterialesItem);
        if (registroStockMaterialesCollectionIdentifiers.includes(registroStockMaterialesIdentifier)) {
          return false;
        }
        registroStockMaterialesCollectionIdentifiers.push(registroStockMaterialesIdentifier);
        return true;
      });
      return [...registroStockMaterialesToAdd, ...registroStockMaterialesCollection];
    }
    return registroStockMaterialesCollection;
  }

  protected convertDateFromClient<T extends IRegistroStockMateriales | NewRegistroStockMateriales | PartialUpdateRegistroStockMateriales>(
    registroStockMateriales: T
  ): RestOf<T> {
    return {
      ...registroStockMateriales,
      fecha: registroStockMateriales.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRegistroStockMateriales: RestRegistroStockMateriales): IRegistroStockMateriales {
    return {
      ...restRegistroStockMateriales,
      fecha: restRegistroStockMateriales.fecha ? dayjs(restRegistroStockMateriales.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRegistroStockMateriales>): HttpResponse<IRegistroStockMateriales> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRegistroStockMateriales[]>): HttpResponse<IRegistroStockMateriales[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
