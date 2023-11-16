import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotaCreditoFormService } from './nota-credito-form.service';
import { NotaCreditoService } from '../service/nota-credito.service';
import { INotaCredito } from '../nota-credito.model';
import { IFacturas } from 'app/entities/facturas/facturas.model';
import { FacturasService } from 'app/entities/facturas/service/facturas.service';

import { NotaCreditoUpdateComponent } from './nota-credito-update.component';

describe('NotaCredito Management Update Component', () => {
  let comp: NotaCreditoUpdateComponent;
  let fixture: ComponentFixture<NotaCreditoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notaCreditoFormService: NotaCreditoFormService;
  let notaCreditoService: NotaCreditoService;
  let facturasService: FacturasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotaCreditoUpdateComponent],
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
      .overrideTemplate(NotaCreditoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotaCreditoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notaCreditoFormService = TestBed.inject(NotaCreditoFormService);
    notaCreditoService = TestBed.inject(NotaCreditoService);
    facturasService = TestBed.inject(FacturasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Facturas query and add missing value', () => {
      const notaCredito: INotaCredito = { id: 456 };
      const facturas: IFacturas = { id: 27237 };
      notaCredito.facturas = facturas;

      const facturasCollection: IFacturas[] = [{ id: 4296 }];
      jest.spyOn(facturasService, 'query').mockReturnValue(of(new HttpResponse({ body: facturasCollection })));
      const additionalFacturas = [facturas];
      const expectedCollection: IFacturas[] = [...additionalFacturas, ...facturasCollection];
      jest.spyOn(facturasService, 'addFacturasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ notaCredito });
      comp.ngOnInit();

      expect(facturasService.query).toHaveBeenCalled();
      expect(facturasService.addFacturasToCollectionIfMissing).toHaveBeenCalledWith(
        facturasCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const notaCredito: INotaCredito = { id: 456 };
      const facturas: IFacturas = { id: 52205 };
      notaCredito.facturas = facturas;

      activatedRoute.data = of({ notaCredito });
      comp.ngOnInit();

      expect(comp.facturasSharedCollection).toContain(facturas);
      expect(comp.notaCredito).toEqual(notaCredito);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotaCredito>>();
      const notaCredito = { id: 123 };
      jest.spyOn(notaCreditoFormService, 'getNotaCredito').mockReturnValue(notaCredito);
      jest.spyOn(notaCreditoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notaCredito });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notaCredito }));
      saveSubject.complete();

      // THEN
      expect(notaCreditoFormService.getNotaCredito).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notaCreditoService.update).toHaveBeenCalledWith(expect.objectContaining(notaCredito));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotaCredito>>();
      const notaCredito = { id: 123 };
      jest.spyOn(notaCreditoFormService, 'getNotaCredito').mockReturnValue({ id: null });
      jest.spyOn(notaCreditoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notaCredito: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notaCredito }));
      saveSubject.complete();

      // THEN
      expect(notaCreditoFormService.getNotaCredito).toHaveBeenCalled();
      expect(notaCreditoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotaCredito>>();
      const notaCredito = { id: 123 };
      jest.spyOn(notaCreditoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notaCredito });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notaCreditoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFacturas', () => {
      it('Should forward to facturasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(facturasService, 'compareFacturas');
        comp.compareFacturas(entity, entity2);
        expect(facturasService.compareFacturas).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
