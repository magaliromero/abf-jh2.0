import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { NotaCreditoDetalleService } from '../service/nota-credito-detalle.service';

export const notaCreditoDetalleResolve = (route: ActivatedRouteSnapshot): Observable<null | INotaCreditoDetalle> => {
  const id = route.params['id'];
  if (id) {
    return inject(NotaCreditoDetalleService)
      .find(id)
      .pipe(
        mergeMap((notaCreditoDetalle: HttpResponse<INotaCreditoDetalle>) => {
          if (notaCreditoDetalle.body) {
            return of(notaCreditoDetalle.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default notaCreditoDetalleResolve;
