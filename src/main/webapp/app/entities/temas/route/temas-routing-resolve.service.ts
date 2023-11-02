import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITemas } from '../temas.model';
import { TemasService } from '../service/temas.service';

export const temasResolve = (route: ActivatedRouteSnapshot): Observable<null | ITemas> => {
  const id = route.params['id'];
  if (id) {
    return inject(TemasService)
      .find(id)
      .pipe(
        mergeMap((temas: HttpResponse<ITemas>) => {
          if (temas.body) {
            return of(temas.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default temasResolve;
