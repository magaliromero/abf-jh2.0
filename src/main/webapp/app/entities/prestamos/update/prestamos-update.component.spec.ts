import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PrestamosFormService } from './prestamos-form.service';
import { PrestamosService } from '../service/prestamos.service';
import { IPrestamos } from '../prestamos.model';

import { PrestamosUpdateComponent } from './prestamos-update.component';

describe('Prestamos Management Update Component', () => {
  let comp: PrestamosUpdateComponent;
  let fixture: ComponentFixture<PrestamosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let prestamosFormService: PrestamosFormService;
  let prestamosService: PrestamosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PrestamosUpdateComponent],
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
      .overrideTemplate(PrestamosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrestamosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    prestamosFormService = TestBed.inject(PrestamosFormService);
    prestamosService = TestBed.inject(PrestamosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const prestamos: IPrestamos = { id: 456 };

      activatedRoute.data = of({ prestamos });
      comp.ngOnInit();

      expect(comp.prestamos).toEqual(prestamos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrestamos>>();
      const prestamos = { id: 123 };
      jest.spyOn(prestamosFormService, 'getPrestamos').mockReturnValue(prestamos);
      jest.spyOn(prestamosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prestamos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prestamos }));
      saveSubject.complete();

      // THEN
      expect(prestamosFormService.getPrestamos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(prestamosService.update).toHaveBeenCalledWith(expect.objectContaining(prestamos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrestamos>>();
      const prestamos = { id: 123 };
      jest.spyOn(prestamosFormService, 'getPrestamos').mockReturnValue({ id: null });
      jest.spyOn(prestamosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prestamos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: prestamos }));
      saveSubject.complete();

      // THEN
      expect(prestamosFormService.getPrestamos).toHaveBeenCalled();
      expect(prestamosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrestamos>>();
      const prestamos = { id: 123 };
      jest.spyOn(prestamosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ prestamos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(prestamosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
