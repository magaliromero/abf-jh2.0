import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nota-credito.test-samples';

import { NotaCreditoFormService } from './nota-credito-form.service';

describe('NotaCredito Form Service', () => {
  let service: NotaCreditoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotaCreditoFormService);
  });

  describe('Service methods', () => {
    describe('createNotaCreditoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNotaCreditoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fecha: expect.any(Object),
            timbrado: expect.any(Object),
            notaNro: expect.any(Object),
            puntoExpedicion: expect.any(Object),
            sucursal: expect.any(Object),
            razonSocial: expect.any(Object),
            ruc: expect.any(Object),
            direccion: expect.any(Object),
            motivoEmision: expect.any(Object),
            estado: expect.any(Object),
            total: expect.any(Object),
            facturas: expect.any(Object),
          })
        );
      });

      it('passing INotaCredito should create a new form with FormGroup', () => {
        const formGroup = service.createNotaCreditoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fecha: expect.any(Object),
            timbrado: expect.any(Object),
            notaNro: expect.any(Object),
            puntoExpedicion: expect.any(Object),
            sucursal: expect.any(Object),
            razonSocial: expect.any(Object),
            ruc: expect.any(Object),
            direccion: expect.any(Object),
            motivoEmision: expect.any(Object),
            estado: expect.any(Object),
            total: expect.any(Object),
            facturas: expect.any(Object),
          })
        );
      });
    });

    describe('getNotaCredito', () => {
      it('should return NewNotaCredito for default NotaCredito initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNotaCreditoFormGroup(sampleWithNewData);

        const notaCredito = service.getNotaCredito(formGroup) as any;

        expect(notaCredito).toMatchObject(sampleWithNewData);
      });

      it('should return NewNotaCredito for empty NotaCredito initial value', () => {
        const formGroup = service.createNotaCreditoFormGroup();

        const notaCredito = service.getNotaCredito(formGroup) as any;

        expect(notaCredito).toMatchObject({});
      });

      it('should return INotaCredito', () => {
        const formGroup = service.createNotaCreditoFormGroup(sampleWithRequiredData);

        const notaCredito = service.getNotaCredito(formGroup) as any;

        expect(notaCredito).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INotaCredito should not enable id FormControl', () => {
        const formGroup = service.createNotaCreditoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNotaCredito should disable id FormControl', () => {
        const formGroup = service.createNotaCreditoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
