import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductos } from '../productos.model';
import { ProductosService } from '../service/productos.service';

export const productosResolve = (route: ActivatedRouteSnapshot): Observable<null | IProductos> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProductosService)
      .find(id)
      .pipe(
        mergeMap((productos: HttpResponse<IProductos>) => {
          if (productos.body) {
            return of(productos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default productosResolve;
