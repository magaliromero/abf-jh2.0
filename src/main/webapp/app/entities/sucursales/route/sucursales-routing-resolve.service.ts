import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISucursales } from '../sucursales.model';
import { SucursalesService } from '../service/sucursales.service';

export const sucursalesResolve = (route: ActivatedRouteSnapshot): Observable<null | ISucursales> => {
  const id = route.params['id'];
  if (id) {
    return inject(SucursalesService)
      .find(id)
      .pipe(
        mergeMap((sucursales: HttpResponse<ISucursales>) => {
          if (sucursales.body) {
            return of(sucursales.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sucursalesResolve;
