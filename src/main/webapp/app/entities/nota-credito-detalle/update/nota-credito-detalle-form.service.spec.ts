import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nota-credito-detalle.test-samples';

import { NotaCreditoDetalleFormService } from './nota-credito-detalle-form.service';

describe('NotaCreditoDetalle Form Service', () => {
  let service: NotaCreditoDetalleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotaCreditoDetalleFormService);
  });

  describe('Service methods', () => {
    describe('createNotaCreditoDetalleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNotaCreditoDetalleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cantidad: expect.any(Object),
            precioUnitario: expect.any(Object),
            subtotal: expect.any(Object),
            porcentajeIva: expect.any(Object),
            valorPorcentaje: expect.any(Object),
            notaCredito: expect.any(Object),
            producto: expect.any(Object),
          })
        );
      });

      it('passing INotaCreditoDetalle should create a new form with FormGroup', () => {
        const formGroup = service.createNotaCreditoDetalleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cantidad: expect.any(Object),
            precioUnitario: expect.any(Object),
            subtotal: expect.any(Object),
            porcentajeIva: expect.any(Object),
            valorPorcentaje: expect.any(Object),
            notaCredito: expect.any(Object),
            producto: expect.any(Object),
          })
        );
      });
    });

    describe('getNotaCreditoDetalle', () => {
      it('should return NewNotaCreditoDetalle for default NotaCreditoDetalle initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNotaCreditoDetalleFormGroup(sampleWithNewData);

        const notaCreditoDetalle = service.getNotaCreditoDetalle(formGroup) as any;

        expect(notaCreditoDetalle).toMatchObject(sampleWithNewData);
      });

      it('should return NewNotaCreditoDetalle for empty NotaCreditoDetalle initial value', () => {
        const formGroup = service.createNotaCreditoDetalleFormGroup();

        const notaCreditoDetalle = service.getNotaCreditoDetalle(formGroup) as any;

        expect(notaCreditoDetalle).toMatchObject({});
      });

      it('should return INotaCreditoDetalle', () => {
        const formGroup = service.createNotaCreditoDetalleFormGroup(sampleWithRequiredData);

        const notaCreditoDetalle = service.getNotaCreditoDetalle(formGroup) as any;

        expect(notaCreditoDetalle).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INotaCreditoDetalle should not enable id FormControl', () => {
        const formGroup = service.createNotaCreditoDetalleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNotaCreditoDetalle should disable id FormControl', () => {
        const formGroup = service.createNotaCreditoDetalleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
