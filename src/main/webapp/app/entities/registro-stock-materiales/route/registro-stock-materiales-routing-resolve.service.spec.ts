import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRegistroStockMateriales } from '../registro-stock-materiales.model';
import { RegistroStockMaterialesService } from '../service/registro-stock-materiales.service';

import { RegistroStockMaterialesRoutingResolveService } from './registro-stock-materiales-routing-resolve.service';

describe('RegistroStockMateriales routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RegistroStockMaterialesRoutingResolveService;
  let service: RegistroStockMaterialesService;
  let resultRegistroStockMateriales: IRegistroStockMateriales | null | undefined;

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
    routingResolveService = TestBed.inject(RegistroStockMaterialesRoutingResolveService);
    service = TestBed.inject(RegistroStockMaterialesService);
    resultRegistroStockMateriales = undefined;
  });

  describe('resolve', () => {
    it('should return IRegistroStockMateriales returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRegistroStockMateriales = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRegistroStockMateriales).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRegistroStockMateriales = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRegistroStockMateriales).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IRegistroStockMateriales>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRegistroStockMateriales = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRegistroStockMateriales).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
