import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MallaCurricularFormService } from './malla-curricular-form.service';
import { MallaCurricularService } from '../service/malla-curricular.service';
import { IMallaCurricular } from '../malla-curricular.model';
import { ITemas } from 'app/entities/temas/temas.model';
import { TemasService } from 'app/entities/temas/service/temas.service';

import { MallaCurricularUpdateComponent } from './malla-curricular-update.component';

describe('MallaCurricular Management Update Component', () => {
  let comp: MallaCurricularUpdateComponent;
  let fixture: ComponentFixture<MallaCurricularUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mallaCurricularFormService: MallaCurricularFormService;
  let mallaCurricularService: MallaCurricularService;
  let temasService: TemasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MallaCurricularUpdateComponent],
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
      .overrideTemplate(MallaCurricularUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MallaCurricularUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mallaCurricularFormService = TestBed.inject(MallaCurricularFormService);
    mallaCurricularService = TestBed.inject(MallaCurricularService);
    temasService = TestBed.inject(TemasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Temas query and add missing value', () => {
      const mallaCurricular: IMallaCurricular = { id: 456 };
      const temas: ITemas[] = [{ id: 48097 }];
      mallaCurricular.temas = temas;

      const temasCollection: ITemas[] = [{ id: 12801 }];
      jest.spyOn(temasService, 'query').mockReturnValue(of(new HttpResponse({ body: temasCollection })));
      const additionalTemas = [...temas];
      const expectedCollection: ITemas[] = [...additionalTemas, ...temasCollection];
      jest.spyOn(temasService, 'addTemasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mallaCurricular });
      comp.ngOnInit();

      expect(temasService.query).toHaveBeenCalled();
      expect(temasService.addTemasToCollectionIfMissing).toHaveBeenCalledWith(
        temasCollection,
        ...additionalTemas.map(expect.objectContaining)
      );
      expect(comp.temasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mallaCurricular: IMallaCurricular = { id: 456 };
      const temas: ITemas = { id: 96523 };
      mallaCurricular.temas = [temas];

      activatedRoute.data = of({ mallaCurricular });
      comp.ngOnInit();

      expect(comp.temasSharedCollection).toContain(temas);
      expect(comp.mallaCurricular).toEqual(mallaCurricular);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMallaCurricular>>();
      const mallaCurricular = { id: 123 };
      jest.spyOn(mallaCurricularFormService, 'getMallaCurricular').mockReturnValue(mallaCurricular);
      jest.spyOn(mallaCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mallaCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mallaCurricular }));
      saveSubject.complete();

      // THEN
      expect(mallaCurricularFormService.getMallaCurricular).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mallaCurricularService.update).toHaveBeenCalledWith(expect.objectContaining(mallaCurricular));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMallaCurricular>>();
      const mallaCurricular = { id: 123 };
      jest.spyOn(mallaCurricularFormService, 'getMallaCurricular').mockReturnValue({ id: null });
      jest.spyOn(mallaCurricularService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mallaCurricular: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mallaCurricular }));
      saveSubject.complete();

      // THEN
      expect(mallaCurricularFormService.getMallaCurricular).toHaveBeenCalled();
      expect(mallaCurricularService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMallaCurricular>>();
      const mallaCurricular = { id: 123 };
      jest.spyOn(mallaCurricularService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mallaCurricular });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mallaCurricularService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTemas', () => {
      it('Should forward to temasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(temasService, 'compareTemas');
        comp.compareTemas(entity, entity2);
        expect(temasService.compareTemas).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
