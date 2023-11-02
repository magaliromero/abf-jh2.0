import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotaCredito } from '../nota-credito.model';
import { NotaCreditoService } from '../service/nota-credito.service';

export const notaCreditoResolve = (route: ActivatedRouteSnapshot): Observable<null | INotaCredito> => {
  const id = route.params['id'];
  if (id) {
    return inject(NotaCreditoService)
      .find(id)
      .pipe(
        mergeMap((notaCredito: HttpResponse<INotaCredito>) => {
          if (notaCredito.body) {
            return of(notaCredito.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default notaCreditoResolve;
