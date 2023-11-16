import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../registro-stock-materiales.test-samples';

import { RegistroStockMaterialesFormService } from './registro-stock-materiales-form.service';

describe('RegistroStockMateriales Form Service', () => {
  let service: RegistroStockMaterialesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegistroStockMaterialesFormService);
  });

  describe('Service methods', () => {
    describe('createRegistroStockMaterialesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRegistroStockMaterialesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comentario: expect.any(Object),
            cantidadInicial: expect.any(Object),
            cantidadModificada: expect.any(Object),
            fecha: expect.any(Object),
            materiales: expect.any(Object),
          })
        );
      });

      it('passing IRegistroStockMateriales should create a new form with FormGroup', () => {
        const formGroup = service.createRegistroStockMaterialesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comentario: expect.any(Object),
            cantidadInicial: expect.any(Object),
            cantidadModificada: expect.any(Object),
            fecha: expect.any(Object),
            materiales: expect.any(Object),
          })
        );
      });
    });

    describe('getRegistroStockMateriales', () => {
      it('should return NewRegistroStockMateriales for default RegistroStockMateriales initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRegistroStockMaterialesFormGroup(sampleWithNewData);

        const registroStockMateriales = service.getRegistroStockMateriales(formGroup) as any;

        expect(registroStockMateriales).toMatchObject(sampleWithNewData);
      });

      it('should return NewRegistroStockMateriales for empty RegistroStockMateriales initial value', () => {
        const formGroup = service.createRegistroStockMaterialesFormGroup();

        const registroStockMateriales = service.getRegistroStockMateriales(formGroup) as any;

        expect(registroStockMateriales).toMatchObject({});
      });

      it('should return IRegistroStockMateriales', () => {
        const formGroup = service.createRegistroStockMaterialesFormGroup(sampleWithRequiredData);

        const registroStockMateriales = service.getRegistroStockMateriales(formGroup) as any;

        expect(registroStockMateriales).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRegistroStockMateriales should not enable id FormControl', () => {
        const formGroup = service.createRegistroStockMaterialesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRegistroStockMateriales should disable id FormControl', () => {
        const formGroup = service.createRegistroStockMaterialesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
