import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { NotaCreditoDetalleService } from '../service/nota-credito-detalle.service';

import { NotaCreditoDetalleRoutingResolveService } from './nota-credito-detalle-routing-resolve.service';

describe('NotaCreditoDetalle routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NotaCreditoDetalleRoutingResolveService;
  let service: NotaCreditoDetalleService;
  let resultNotaCreditoDetalle: INotaCreditoDetalle | null | undefined;

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
    routingResolveService = TestBed.inject(NotaCreditoDetalleRoutingResolveService);
    service = TestBed.inject(NotaCreditoDetalleService);
    resultNotaCreditoDetalle = undefined;
  });

  describe('resolve', () => {
    it('should return INotaCreditoDetalle returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNotaCreditoDetalle = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNotaCreditoDetalle).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNotaCreditoDetalle = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNotaCreditoDetalle).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<INotaCreditoDetalle>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNotaCreditoDetalle = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNotaCreditoDetalle).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
