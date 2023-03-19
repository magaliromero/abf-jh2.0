import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFichaPartidasTorneos } from '../ficha-partidas-torneos.model';
import { FichaPartidasTorneosService } from '../service/ficha-partidas-torneos.service';

@Injectable({ providedIn: 'root' })
export class FichaPartidasTorneosRoutingResolveService implements Resolve<IFichaPartidasTorneos | null> {
  constructor(protected service: FichaPartidasTorneosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFichaPartidasTorneos | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fichaPartidasTorneos: HttpResponse<IFichaPartidasTorneos>) => {
          if (fichaPartidasTorneos.body) {
            return of(fichaPartidasTorneos.body);
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
