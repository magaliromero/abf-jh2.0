import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAlumnos } from '../alumnos.model';
import { AlumnosService } from '../service/alumnos.service';

export const alumnosResolve = (route: ActivatedRouteSnapshot): Observable<null | IAlumnos> => {
  const id = route.params['id'];
  if (id) {
    return inject(AlumnosService)
      .find(id)
      .pipe(
        mergeMap((alumnos: HttpResponse<IAlumnos>) => {
          if (alumnos.body) {
            return of(alumnos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default alumnosResolve;
