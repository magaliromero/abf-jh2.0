import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SucursalesFormService } from './sucursales-form.service';
import { SucursalesService } from '../service/sucursales.service';
import { ISucursales } from '../sucursales.model';
import { ITimbrados } from 'app/entities/timbrados/timbrados.model';
import { TimbradosService } from 'app/entities/timbrados/service/timbrados.service';

import { SucursalesUpdateComponent } from './sucursales-update.component';

describe('Sucursales Management Update Component', () => {
  let comp: SucursalesUpdateComponent;
  let fixture: ComponentFixture<SucursalesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sucursalesFormService: SucursalesFormService;
  let sucursalesService: SucursalesService;
  let timbradosService: TimbradosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SucursalesUpdateComponent],
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
      .overrideTemplate(SucursalesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SucursalesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sucursalesFormService = TestBed.inject(SucursalesFormService);
    sucursalesService = TestBed.inject(SucursalesService);
    timbradosService = TestBed.inject(TimbradosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Timbrados query and add missing value', () => {
      const sucursales: ISucursales = { id: 456 };
      const timbrados: ITimbrados = { id: 64794 };
      sucursales.timbrados = timbrados;

      const timbradosCollection: ITimbrados[] = [{ id: 93690 }];
      jest.spyOn(timbradosService, 'query').mockReturnValue(of(new HttpResponse({ body: timbradosCollection })));
      const additionalTimbrados = [timbrados];
      const expectedCollection: ITimbrados[] = [...additionalTimbrados, ...timbradosCollection];
      jest.spyOn(timbradosService, 'addTimbradosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sucursales });
      comp.ngOnInit();

      expect(timbradosService.query).toHaveBeenCalled();
      expect(timbradosService.addTimbradosToCollectionIfMissing).toHaveBeenCalledWith(
        timbradosCollection,
        ...additionalTimbrados.map(expect.objectContaining)
      );
      expect(comp.timbradosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sucursales: ISucursales = { id: 456 };
      const timbrados: ITimbrados = { id: 6042 };
      sucursales.timbrados = timbrados;

      activatedRoute.data = of({ sucursales });
      comp.ngOnInit();

      expect(comp.timbradosSharedCollection).toContain(timbrados);
      expect(comp.sucursales).toEqual(sucursales);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISucursales>>();
      const sucursales = { id: 123 };
      jest.spyOn(sucursalesFormService, 'getSucursales').mockReturnValue(sucursales);
      jest.spyOn(sucursalesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sucursales });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sucursales }));
      saveSubject.complete();

      // THEN
      expect(sucursalesFormService.getSucursales).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sucursalesService.update).toHaveBeenCalledWith(expect.objectContaining(sucursales));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISucursales>>();
      const sucursales = { id: 123 };
      jest.spyOn(sucursalesFormService, 'getSucursales').mockReturnValue({ id: null });
      jest.spyOn(sucursalesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sucursales: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sucursales }));
      saveSubject.complete();

      // THEN
      expect(sucursalesFormService.getSucursales).toHaveBeenCalled();
      expect(sucursalesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISucursales>>();
      const sucursales = { id: 123 };
      jest.spyOn(sucursalesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sucursales });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sucursalesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTimbrados', () => {
      it('Should forward to timbradosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(timbradosService, 'compareTimbrados');
        comp.compareTimbrados(entity, entity2);
        expect(timbradosService.compareTimbrados).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
