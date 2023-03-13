import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../torneos.test-samples';

import { TorneosFormService } from './torneos-form.service';

describe('Torneos Form Service', () => {
  let service: TorneosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TorneosFormService);
  });

  describe('Service methods', () => {
    describe('createTorneosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTorneosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreTorneo: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaFin: expect.any(Object),
            lugar: expect.any(Object),
            tiempo: expect.any(Object),
            tipoTorneo: expect.any(Object),
            torneoEvaluado: expect.any(Object),
            federado: expect.any(Object),
          })
        );
      });

      it('passing ITorneos should create a new form with FormGroup', () => {
        const formGroup = service.createTorneosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreTorneo: expect.any(Object),
            fechaInicio: expect.any(Object),
            fechaFin: expect.any(Object),
            lugar: expect.any(Object),
            tiempo: expect.any(Object),
            tipoTorneo: expect.any(Object),
            torneoEvaluado: expect.any(Object),
            federado: expect.any(Object),
          })
        );
      });
    });

    describe('getTorneos', () => {
      it('should return NewTorneos for default Torneos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTorneosFormGroup(sampleWithNewData);

        const torneos = service.getTorneos(formGroup) as any;

        expect(torneos).toMatchObject(sampleWithNewData);
      });

      it('should return NewTorneos for empty Torneos initial value', () => {
        const formGroup = service.createTorneosFormGroup();

        const torneos = service.getTorneos(formGroup) as any;

        expect(torneos).toMatchObject({});
      });

      it('should return ITorneos', () => {
        const formGroup = service.createTorneosFormGroup(sampleWithRequiredData);

        const torneos = service.getTorneos(formGroup) as any;

        expect(torneos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITorneos should not enable id FormControl', () => {
        const formGroup = service.createTorneosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTorneos should disable id FormControl', () => {
        const formGroup = service.createTorneosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
