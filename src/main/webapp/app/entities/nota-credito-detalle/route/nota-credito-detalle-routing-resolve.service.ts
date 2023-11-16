import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { NotaCreditoDetalleService } from '../service/nota-credito-detalle.service';

@Injectable({ providedIn: 'root' })
export class NotaCreditoDetalleRoutingResolveService implements Resolve<INotaCreditoDetalle | null> {
  constructor(protected service: NotaCreditoDetalleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotaCreditoDetalle | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((notaCreditoDetalle: HttpResponse<INotaCreditoDetalle>) => {
          if (notaCreditoDetalle.body) {
            return of(notaCreditoDetalle.body);
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
