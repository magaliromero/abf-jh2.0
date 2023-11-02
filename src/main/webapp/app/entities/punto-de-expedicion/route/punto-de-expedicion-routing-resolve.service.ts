import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPuntoDeExpedicion } from '../punto-de-expedicion.model';
import { PuntoDeExpedicionService } from '../service/punto-de-expedicion.service';

export const puntoDeExpedicionResolve = (route: ActivatedRouteSnapshot): Observable<null | IPuntoDeExpedicion> => {
  const id = route.params['id'];
  if (id) {
    return inject(PuntoDeExpedicionService)
      .find(id)
      .pipe(
        mergeMap((puntoDeExpedicion: HttpResponse<IPuntoDeExpedicion>) => {
          if (puntoDeExpedicion.body) {
            return of(puntoDeExpedicion.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default puntoDeExpedicionResolve;
