import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMateriales } from '../materiales.model';
import { MaterialesService } from '../service/materiales.service';

export const materialesResolve = (route: ActivatedRouteSnapshot): Observable<null | IMateriales> => {
  const id = route.params['id'];
  if (id) {
    return inject(MaterialesService)
      .find(id)
      .pipe(
        mergeMap((materiales: HttpResponse<IMateriales>) => {
          if (materiales.body) {
            return of(materiales.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default materialesResolve;
