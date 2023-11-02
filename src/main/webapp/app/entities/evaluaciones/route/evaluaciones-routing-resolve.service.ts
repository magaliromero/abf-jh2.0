import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluaciones } from '../evaluaciones.model';
import { EvaluacionesService } from '../service/evaluaciones.service';

export const evaluacionesResolve = (route: ActivatedRouteSnapshot): Observable<null | IEvaluaciones> => {
  const id = route.params['id'];
  if (id) {
    return inject(EvaluacionesService)
      .find(id)
      .pipe(
        mergeMap((evaluaciones: HttpResponse<IEvaluaciones>) => {
          if (evaluaciones.body) {
            return of(evaluaciones.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default evaluacionesResolve;
