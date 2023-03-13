import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IFichaPartidasTorneos } from '../ficha-partidas-torneos.model';
import { FichaPartidasTorneosService } from '../service/ficha-partidas-torneos.service';

import { FichaPartidasTorneosRoutingResolveService } from './ficha-partidas-torneos-routing-resolve.service';

describe('FichaPartidasTorneos routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FichaPartidasTorneosRoutingResolveService;
  let service: FichaPartidasTorneosService;
  let resultFichaPartidasTorneos: IFichaPartidasTorneos | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(FichaPartidasTorneosRoutingResolveService);
    service = TestBed.inject(FichaPartidasTorneosService);
    resultFichaPartidasTorneos = undefined;
  });

  describe('resolve', () => {
    it('should return IFichaPartidasTorneos returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFichaPartidasTorneos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFichaPartidasTorneos).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFichaPartidasTorneos = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFichaPartidasTorneos).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFichaPartidasTorneos>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFichaPartidasTorneos = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultFichaPartidasTorneos).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
