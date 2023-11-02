import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITiposDocumentos } from '../tipos-documentos.model';
import { TiposDocumentosService } from '../service/tipos-documentos.service';

export const tiposDocumentosResolve = (route: ActivatedRouteSnapshot): Observable<null | ITiposDocumentos> => {
  const id = route.params['id'];
  if (id) {
    return inject(TiposDocumentosService)
      .find(id)
      .pipe(
        mergeMap((tiposDocumentos: HttpResponse<ITiposDocumentos>) => {
          if (tiposDocumentos.body) {
            return of(tiposDocumentos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tiposDocumentosResolve;
