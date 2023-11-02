import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuncionarios } from '../funcionarios.model';
import { FuncionariosService } from '../service/funcionarios.service';

export const funcionariosResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuncionarios> => {
  const id = route.params['id'];
  if (id) {
    return inject(FuncionariosService)
      .find(id)
      .pipe(
        mergeMap((funcionarios: HttpResponse<IFuncionarios>) => {
          if (funcionarios.body) {
            return of(funcionarios.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default funcionariosResolve;
