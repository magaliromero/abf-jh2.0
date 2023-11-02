import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { INotaCredito } from 'app/entities/nota-credito/nota-credito.model';
import { NotaCreditoService } from 'app/entities/nota-credito/service/nota-credito.service';
import { IProductos } from 'app/entities/productos/productos.model';
import { ProductosService } from 'app/entities/productos/service/productos.service';
import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { NotaCreditoDetalleService } from '../service/nota-credito-detalle.service';
import { NotaCreditoDetalleFormService } from './nota-credito-detalle-form.service';

import { NotaCreditoDetalleUpdateComponent } from './nota-credito-detalle-update.component';

describe('NotaCreditoDetalle Management Update Component', () => {
  let comp: NotaCreditoDetalleUpdateComponent;
  let fixture: ComponentFixture<NotaCreditoDetalleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notaCreditoDetalleFormService: NotaCreditoDetalleFormService;
  let notaCreditoDetalleService: NotaCreditoDetalleService;
  let notaCreditoService: NotaCreditoService;
  let productosService: ProductosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), NotaCreditoDetalleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(NotaCreditoDetalleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotaCreditoDetalleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notaCreditoDetalleFormService = TestBed.inject(NotaCreditoDetalleFormService);
    notaCreditoDetalleService = TestBed.inject(NotaCreditoDetalleService);
    notaCreditoService = TestBed.inject(NotaCreditoService);
    productosService = TestBed.inject(ProductosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call NotaCredito query and add missing value', () => {
      const notaCreditoDetalle: INotaCreditoDetalle = { id: 456 };
      const notaCredito: INotaCredito = { id: 4028 };
      notaCreditoDetalle.notaCredito = notaCredito;

      const notaCreditoCollection: INotaCredito[] = [{ id: 27049 }];
      jest.spyOn(notaCreditoService, 'query').mockReturnValue(of(new HttpResponse({ body: notaCreditoCollection })));
      const additionalNotaCreditos = [notaCredito];
      const expectedCollection: INotaCredito[] = [...additionalNotaCreditos, ...notaCreditoCollection];
      jest.spyOn(notaCreditoService, 'addNotaCreditoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notaCreditoDetalle });
      comp.ngOnInit();

      expect(notaCreditoService.query).toHaveBeenCalled();
      expect(notaCreditoService.addNotaCreditoToCollectionIfMissing).toHaveBeenCalledWith(
        notaCreditoCollection,
        ...additionalNotaCreditos.map(expect.objectContaining),
      );
      expect(comp.notaCreditosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Productos query and add missing value', () => {
      const notaCreditoDetalle: INotaCreditoDetalle = { id: 456 };
      const producto: IProductos = { id: 29734 };
      notaCreditoDetalle.producto = producto;

      const productosCollection: IProductos[] = [{ id: 30519 }];
      jest.spyOn(productosService, 'query').mockReturnValue(of(new HttpResponse({ body: productosCollection })));
      const additionalProductos = [producto];
      const expectedCollection: IProductos[] = [...additionalProductos, ...productosCollection];
      jest.spyOn(productosService, 'addProductosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notaCreditoDetalle });
      comp.ngOnInit();

      expect(productosService.query).toHaveBeenCalled();
      expect(productosService.addProductosToCollectionIfMissing).toHaveBeenCalledWith(
        productosCollection,
        ...additionalProductos.map(expect.objectContaining),
      );
      expect(comp.productosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notaCreditoDetalle: INotaCreditoDetalle = { id: 456 };
      const notaCredito: INotaCredito = { id: 6371 };
      notaCreditoDetalle.notaCredito = notaCredito;
      const producto: IProductos = { id: 1669 };
      notaCreditoDetalle.producto = producto;

      activatedRoute.data = of({ notaCreditoDetalle });
      comp.ngOnInit();

      expect(comp.notaCreditosSharedCollection).toContain(notaCredito);
      expect(comp.productosSharedCollection).toContain(producto);
      expect(comp.notaCreditoDetalle).toEqual(notaCreditoDetalle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotaCreditoDetalle>>();
      const notaCreditoDetalle = { id: 123 };
      jest.spyOn(notaCreditoDetalleFormService, 'getNotaCreditoDetalle').mockReturnValue(notaCreditoDetalle);
      jest.spyOn(notaCreditoDetalleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notaCreditoDetalle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notaCreditoDetalle }));
      saveSubject.complete();

      // THEN
      expect(notaCreditoDetalleFormService.getNotaCreditoDetalle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notaCreditoDetalleService.update).toHaveBeenCalledWith(expect.objectContaining(notaCreditoDetalle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotaCreditoDetalle>>();
      const notaCreditoDetalle = { id: 123 };
      jest.spyOn(notaCreditoDetalleFormService, 'getNotaCreditoDetalle').mockReturnValue({ id: null });
      jest.spyOn(notaCreditoDetalleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notaCreditoDetalle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notaCreditoDetalle }));
      saveSubject.complete();

      // THEN
      expect(notaCreditoDetalleFormService.getNotaCreditoDetalle).toHaveBeenCalled();
      expect(notaCreditoDetalleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotaCreditoDetalle>>();
      const notaCreditoDetalle = { id: 123 };
      jest.spyOn(notaCreditoDetalleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notaCreditoDetalle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notaCreditoDetalleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareNotaCredito', () => {
      it('Should forward to notaCreditoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(notaCreditoService, 'compareNotaCredito');
        comp.compareNotaCredito(entity, entity2);
        expect(notaCreditoService.compareNotaCredito).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProductos', () => {
      it('Should forward to productosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productosService, 'compareProductos');
        comp.compareProductos(entity, entity2);
        expect(productosService.compareProductos).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
