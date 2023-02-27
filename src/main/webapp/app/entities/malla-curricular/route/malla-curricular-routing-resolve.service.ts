import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMallaCurricular } from '../malla-curricular.model';
import { MallaCurricularService } from '../service/malla-curricular.service';

@Injectable({ providedIn: 'root' })
export class MallaCurricularRoutingResolveService implements Resolve<IMallaCurricular | null> {
  constructor(protected service: MallaCurricularService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMallaCurricular | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mallaCurricular: HttpResponse<IMallaCurricular>) => {
          if (mallaCurricular.body) {
            return of(mallaCurricular.body);
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
