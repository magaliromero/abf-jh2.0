import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRegistroClases } from '../registro-clases.model';
import { RegistroClasesService } from '../service/registro-clases.service';

export const registroClasesResolve = (route: ActivatedRouteSnapshot): Observable<null | IRegistroClases> => {
  const id = route.params['id'];
  if (id) {
    return inject(RegistroClasesService)
      .find(id)
      .pipe(
        mergeMap((registroClases: HttpResponse<IRegistroClases>) => {
          if (registroClases.body) {
            return of(registroClases.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default registroClasesResolve;
