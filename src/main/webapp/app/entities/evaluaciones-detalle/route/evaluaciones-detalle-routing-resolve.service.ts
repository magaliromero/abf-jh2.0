import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluacionesDetalle } from '../evaluaciones-detalle.model';
import { EvaluacionesDetalleService } from '../service/evaluaciones-detalle.service';

export const evaluacionesDetalleResolve = (route: ActivatedRouteSnapshot): Observable<null | IEvaluacionesDetalle> => {
  const id = route.params['id'];
  if (id) {
    return inject(EvaluacionesDetalleService)
      .find(id)
      .pipe(
        mergeMap((evaluacionesDetalle: HttpResponse<IEvaluacionesDetalle>) => {
          if (evaluacionesDetalle.body) {
            return of(evaluacionesDetalle.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default evaluacionesDetalleResolve;
