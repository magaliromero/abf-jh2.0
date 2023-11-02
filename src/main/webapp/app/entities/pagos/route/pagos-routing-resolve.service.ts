import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPagos } from '../pagos.model';
import { PagosService } from '../service/pagos.service';

export const pagosResolve = (route: ActivatedRouteSnapshot): Observable<null | IPagos> => {
  const id = route.params['id'];
  if (id) {
    return inject(PagosService)
      .find(id)
      .pipe(
        mergeMap((pagos: HttpResponse<IPagos>) => {
          if (pagos.body) {
            return of(pagos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default pagosResolve;
