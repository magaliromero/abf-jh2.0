import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITimbrados } from '../timbrados.model';
import { TimbradosService } from '../service/timbrados.service';

export const timbradosResolve = (route: ActivatedRouteSnapshot): Observable<null | ITimbrados> => {
  const id = route.params['id'];
  if (id) {
    return inject(TimbradosService)
      .find(id)
      .pipe(
        mergeMap((timbrados: HttpResponse<ITimbrados>) => {
          if (timbrados.body) {
            return of(timbrados.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default timbradosResolve;
