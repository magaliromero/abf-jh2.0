import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFacturas } from '../facturas.model';
import { FacturasService } from '../service/facturas.service';

export const facturasResolve = (route: ActivatedRouteSnapshot): Observable<null | IFacturas> => {
  const id = route.params['id'];
  if (id) {
    return inject(FacturasService)
      .find(id)
      .pipe(
        mergeMap((facturas: HttpResponse<IFacturas>) => {
          if (facturas.body) {
            return of(facturas.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default facturasResolve;
