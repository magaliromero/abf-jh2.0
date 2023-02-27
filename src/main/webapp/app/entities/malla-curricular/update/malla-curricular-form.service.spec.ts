import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../malla-curricular.test-samples';

import { MallaCurricularFormService } from './malla-curricular-form.service';

describe('MallaCurricular Form Service', () => {
  let service: MallaCurricularFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MallaCurricularFormService);
  });

  describe('Service methods', () => {
    describe('createMallaCurricularFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMallaCurricularFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            nivel: expect.any(Object),
            temas: expect.any(Object),
          })
        );
      });

      it('passing IMallaCurricular should create a new form with FormGroup', () => {
        const formGroup = service.createMallaCurricularFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            titulo: expect.any(Object),
            nivel: expect.any(Object),
            temas: expect.any(Object),
          })
        );
      });
    });

    describe('getMallaCurricular', () => {
      it('should return NewMallaCurricular for default MallaCurricular initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMallaCurricularFormGroup(sampleWithNewData);

        const mallaCurricular = service.getMallaCurricular(formGroup) as any;

        expect(mallaCurricular).toMatchObject(sampleWithNewData);
      });

      it('should return NewMallaCurricular for empty MallaCurricular initial value', () => {
        const formGroup = service.createMallaCurricularFormGroup();

        const mallaCurricular = service.getMallaCurricular(formGroup) as any;

        expect(mallaCurricular).toMatchObject({});
      });

      it('should return IMallaCurricular', () => {
        const formGroup = service.createMallaCurricularFormGroup(sampleWithRequiredData);

        const mallaCurricular = service.getMallaCurricular(formGroup) as any;

        expect(mallaCurricular).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMallaCurricular should not enable id FormControl', () => {
        const formGroup = service.createMallaCurricularFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMallaCurricular should disable id FormControl', () => {
        const formGroup = service.createMallaCurricularFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
