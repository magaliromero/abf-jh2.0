import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TorneosFormService } from './torneos-form.service';
import { TorneosService } from '../service/torneos.service';
import { ITorneos } from '../torneos.model';

import { TorneosUpdateComponent } from './torneos-update.component';

describe('Torneos Management Update Component', () => {
  let comp: TorneosUpdateComponent;
  let fixture: ComponentFixture<TorneosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let torneosFormService: TorneosFormService;
  let torneosService: TorneosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TorneosUpdateComponent],
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
      .overrideTemplate(TorneosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TorneosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    torneosFormService = TestBed.inject(TorneosFormService);
    torneosService = TestBed.inject(TorneosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const torneos: ITorneos = { id: 456 };

      activatedRoute.data = of({ torneos });
      comp.ngOnInit();

      expect(comp.torneos).toEqual(torneos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITorneos>>();
      const torneos = { id: 123 };
      jest.spyOn(torneosFormService, 'getTorneos').mockReturnValue(torneos);
      jest.spyOn(torneosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ torneos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: torneos }));
      saveSubject.complete();

      // THEN
      expect(torneosFormService.getTorneos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(torneosService.update).toHaveBeenCalledWith(expect.objectContaining(torneos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITorneos>>();
      const torneos = { id: 123 };
      jest.spyOn(torneosFormService, 'getTorneos').mockReturnValue({ id: null });
      jest.spyOn(torneosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ torneos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: torneos }));
      saveSubject.complete();

      // THEN
      expect(torneosFormService.getTorneos).toHaveBeenCalled();
      expect(torneosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITorneos>>();
      const torneos = { id: 123 };
      jest.spyOn(torneosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ torneos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(torneosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
