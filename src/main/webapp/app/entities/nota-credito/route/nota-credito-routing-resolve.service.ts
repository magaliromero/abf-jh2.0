import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INotaCredito } from '../nota-credito.model';
import { NotaCreditoService } from '../service/nota-credito.service';

@Injectable({ providedIn: 'root' })
export class NotaCreditoRoutingResolveService implements Resolve<INotaCredito | null> {
  constructor(protected service: NotaCreditoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotaCredito | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((notaCredito: HttpResponse<INotaCredito>) => {
          if (notaCredito.body) {
            return of(notaCredito.body);
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
