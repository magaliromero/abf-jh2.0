import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInscripciones } from '../inscripciones.model';
import { InscripcionesService } from '../service/inscripciones.service';

export const inscripcionesResolve = (route: ActivatedRouteSnapshot): Observable<null | IInscripciones> => {
  const id = route.params['id'];
  if (id) {
    return inject(InscripcionesService)
      .find(id)
      .pipe(
        mergeMap((inscripciones: HttpResponse<IInscripciones>) => {
          if (inscripciones.body) {
            return of(inscripciones.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default inscripcionesResolve;
