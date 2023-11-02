import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICursos } from '../cursos.model';
import { CursosService } from '../service/cursos.service';

export const cursosResolve = (route: ActivatedRouteSnapshot): Observable<null | ICursos> => {
  const id = route.params['id'];
  if (id) {
    return inject(CursosService)
      .find(id)
      .pipe(
        mergeMap((cursos: HttpResponse<ICursos>) => {
          if (cursos.body) {
            return of(cursos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cursosResolve;
