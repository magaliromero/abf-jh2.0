import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TimbradosService } from '../service/timbrados.service';
import { ITimbrados } from '../timbrados.model';
import { TimbradosFormService } from './timbrados-form.service';

import { TimbradosUpdateComponent } from './timbrados-update.component';

describe('Timbrados Management Update Component', () => {
  let comp: TimbradosUpdateComponent;
  let fixture: ComponentFixture<TimbradosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let timbradosFormService: TimbradosFormService;
  let timbradosService: TimbradosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TimbradosUpdateComponent],
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
      .overrideTemplate(TimbradosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimbradosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    timbradosFormService = TestBed.inject(TimbradosFormService);
    timbradosService = TestBed.inject(TimbradosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const timbrados: ITimbrados = { id: 456 };

      activatedRoute.data = of({ timbrados });
      comp.ngOnInit();

      expect(comp.timbrados).toEqual(timbrados);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimbrados>>();
      const timbrados = { id: 123 };
      jest.spyOn(timbradosFormService, 'getTimbrados').mockReturnValue(timbrados);
      jest.spyOn(timbradosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timbrados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timbrados }));
      saveSubject.complete();

      // THEN
      expect(timbradosFormService.getTimbrados).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(timbradosService.update).toHaveBeenCalledWith(expect.objectContaining(timbrados));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimbrados>>();
      const timbrados = { id: 123 };
      jest.spyOn(timbradosFormService, 'getTimbrados').mockReturnValue({ id: null });
      jest.spyOn(timbradosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timbrados: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timbrados }));
      saveSubject.complete();

      // THEN
      expect(timbradosFormService.getTimbrados).toHaveBeenCalled();
      expect(timbradosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITimbrados>>();
      const timbrados = { id: 123 };
      jest.spyOn(timbradosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timbrados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(timbradosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
