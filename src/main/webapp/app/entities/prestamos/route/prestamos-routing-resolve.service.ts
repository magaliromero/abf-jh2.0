import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrestamos } from '../prestamos.model';
import { PrestamosService } from '../service/prestamos.service';

export const prestamosResolve = (route: ActivatedRouteSnapshot): Observable<null | IPrestamos> => {
  const id = route.params['id'];
  if (id) {
    return inject(PrestamosService)
      .find(id)
      .pipe(
        mergeMap((prestamos: HttpResponse<IPrestamos>) => {
          if (prestamos.body) {
            return of(prestamos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default prestamosResolve;
