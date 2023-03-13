import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITorneos } from '../torneos.model';
import { TorneosService } from '../service/torneos.service';

@Injectable({ providedIn: 'root' })
export class TorneosRoutingResolveService implements Resolve<ITorneos | null> {
  constructor(protected service: TorneosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITorneos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((torneos: HttpResponse<ITorneos>) => {
          if (torneos.body) {
            return of(torneos.body);
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
