import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotaCreditoDetalle, NewNotaCreditoDetalle } from '../nota-credito-detalle.model';

export type PartialUpdateNotaCreditoDetalle = Partial<INotaCreditoDetalle> & Pick<INotaCreditoDetalle, 'id'>;

export type EntityResponseType = HttpResponse<INotaCreditoDetalle>;
export type EntityArrayResponseType = HttpResponse<INotaCreditoDetalle[]>;

@Injectable({ providedIn: 'root' })
export class NotaCreditoDetalleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nota-credito-detalles');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(notaCreditoDetalle: NewNotaCreditoDetalle): Observable<EntityResponseType> {
    return this.http.post<INotaCreditoDetalle>(this.resourceUrl, notaCreditoDetalle, { observe: 'response' });
  }

  update(notaCreditoDetalle: INotaCreditoDetalle): Observable<EntityResponseType> {
    return this.http.put<INotaCreditoDetalle>(
      `${this.resourceUrl}/${this.getNotaCreditoDetalleIdentifier(notaCreditoDetalle)}`,
      notaCreditoDetalle,
      { observe: 'response' },
    );
  }

  partialUpdate(notaCreditoDetalle: PartialUpdateNotaCreditoDetalle): Observable<EntityResponseType> {
    return this.http.patch<INotaCreditoDetalle>(
      `${this.resourceUrl}/${this.getNotaCreditoDetalleIdentifier(notaCreditoDetalle)}`,
      notaCreditoDetalle,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotaCreditoDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotaCreditoDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotaCreditoDetalleIdentifier(notaCreditoDetalle: Pick<INotaCreditoDetalle, 'id'>): number {
    return notaCreditoDetalle.id;
  }

  compareNotaCreditoDetalle(o1: Pick<INotaCreditoDetalle, 'id'> | null, o2: Pick<INotaCreditoDetalle, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotaCreditoDetalleIdentifier(o1) === this.getNotaCreditoDetalleIdentifier(o2) : o1 === o2;
  }

  addNotaCreditoDetalleToCollectionIfMissing<Type extends Pick<INotaCreditoDetalle, 'id'>>(
    notaCreditoDetalleCollection: Type[],
    ...notaCreditoDetallesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notaCreditoDetalles: Type[] = notaCreditoDetallesToCheck.filter(isPresent);
    if (notaCreditoDetalles.length > 0) {
      const notaCreditoDetalleCollectionIdentifiers = notaCreditoDetalleCollection.map(
        notaCreditoDetalleItem => this.getNotaCreditoDetalleIdentifier(notaCreditoDetalleItem)!,
      );
      const notaCreditoDetallesToAdd = notaCreditoDetalles.filter(notaCreditoDetalleItem => {
        const notaCreditoDetalleIdentifier = this.getNotaCreditoDetalleIdentifier(notaCreditoDetalleItem);
        if (notaCreditoDetalleCollectionIdentifiers.includes(notaCreditoDetalleIdentifier)) {
          return false;
        }
        notaCreditoDetalleCollectionIdentifiers.push(notaCreditoDetalleIdentifier);
        return true;
      });
      return [...notaCreditoDetallesToAdd, ...notaCreditoDetalleCollection];
    }
    return notaCreditoDetalleCollection;
  }
}
