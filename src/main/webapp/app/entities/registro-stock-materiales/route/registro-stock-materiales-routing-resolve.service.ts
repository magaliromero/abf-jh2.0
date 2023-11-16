import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRegistroStockMateriales } from '../registro-stock-materiales.model';
import { RegistroStockMaterialesService } from '../service/registro-stock-materiales.service';

@Injectable({ providedIn: 'root' })
export class RegistroStockMaterialesRoutingResolveService implements Resolve<IRegistroStockMateriales | null> {
  constructor(protected service: RegistroStockMaterialesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegistroStockMateriales | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((registroStockMateriales: HttpResponse<IRegistroStockMateriales>) => {
          if (registroStockMateriales.body) {
            return of(registroStockMateriales.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
