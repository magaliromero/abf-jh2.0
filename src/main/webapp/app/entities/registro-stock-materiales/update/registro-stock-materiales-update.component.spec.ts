import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RegistroStockMaterialesFormService } from './registro-stock-materiales-form.service';
import { RegistroStockMaterialesService } from '../service/registro-stock-materiales.service';
import { IRegistroStockMateriales } from '../registro-stock-materiales.model';
import { IMateriales } from 'app/entities/materiales/materiales.model';
import { MaterialesService } from 'app/entities/materiales/service/materiales.service';

import { RegistroStockMaterialesUpdateComponent } from './registro-stock-materiales-update.component';

describe('RegistroStockMateriales Management Update Component', () => {
  let comp: RegistroStockMaterialesUpdateComponent;
  let fixture: ComponentFixture<RegistroStockMaterialesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let registroStockMaterialesFormService: RegistroStockMaterialesFormService;
  let registroStockMaterialesService: RegistroStockMaterialesService;
  let materialesService: MaterialesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RegistroStockMaterialesUpdateComponent],
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
      .overrideTemplate(RegistroStockMaterialesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RegistroStockMaterialesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    registroStockMaterialesFormService = TestBed.inject(RegistroStockMaterialesFormService);
    registroStockMaterialesService = TestBed.inject(RegistroStockMaterialesService);
    materialesService = TestBed.inject(MaterialesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Materiales query and add missing value', () => {
      const registroStockMateriales: IRegistroStockMateriales = { id: 456 };
      const materiales: IMateriales = { id: 5692 };
      registroStockMateriales.materiales = materiales;

      const materialesCollection: IMateriales[] = [{ id: 20897 }];
      jest.spyOn(materialesService, 'query').mockReturnValue(of(new HttpResponse({ body: materialesCollection })));
      const additionalMateriales = [materiales];
      const expectedCollection: IMateriales[] = [...additionalMateriales, ...materialesCollection];
      jest.spyOn(materialesService, 'addMaterialesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ registroStockMateriales });
      comp.ngOnInit();

      expect(materialesService.query).toHaveBeenCalled();
      expect(materialesService.addMaterialesToCollectionIfMissing).toHaveBeenCalledWith(
        materialesCollection,
        ...additionalMateriales.map(expect.objectContaining)
      );
      expect(comp.materialesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const registroStockMateriales: IRegistroStockMateriales = { id: 456 };
      const materiales: IMateriales = { id: 50647 };
      registroStockMateriales.materiales = materiales;

      activatedRoute.data = of({ registroStockMateriales });
      comp.ngOnInit();

      expect(comp.materialesSharedCollection).toContain(materiales);
      expect(comp.registroStockMateriales).toEqual(registroStockMateriales);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegistroStockMateriales>>();
      const registroStockMateriales = { id: 123 };
      jest.spyOn(registroStockMaterialesFormService, 'getRegistroStockMateriales').mockReturnValue(registroStockMateriales);
      jest.spyOn(registroStockMaterialesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ registroStockMateriales });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: registroStockMateriales }));
      saveSubject.complete();

      // THEN
      expect(registroStockMaterialesFormService.getRegistroStockMateriales).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(registroStockMaterialesService.update).toHaveBeenCalledWith(expect.objectContaining(registroStockMateriales));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegistroStockMateriales>>();
      const registroStockMateriales = { id: 123 };
      jest.spyOn(registroStockMaterialesFormService, 'getRegistroStockMateriales').mockReturnValue({ id: null });
      jest.spyOn(registroStockMaterialesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ registroStockMateriales: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: registroStockMateriales }));
      saveSubject.complete();

      // THEN
      expect(registroStockMaterialesFormService.getRegistroStockMateriales).toHaveBeenCalled();
      expect(registroStockMaterialesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegistroStockMateriales>>();
      const registroStockMateriales = { id: 123 };
      jest.spyOn(registroStockMaterialesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ registroStockMateriales });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(registroStockMaterialesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMateriales', () => {
      it('Should forward to materialesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(materialesService, 'compareMateriales');
        comp.compareMateriales(entity, entity2);
        expect(materialesService.compareMateriales).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
