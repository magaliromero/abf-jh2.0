import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMallaCurricular, NewMallaCurricular } from '../malla-curricular.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMallaCurricular for edit and NewMallaCurricularFormGroupInput for create.
 */
type MallaCurricularFormGroupInput = IMallaCurricular | PartialWithRequiredKeyOf<NewMallaCurricular>;

type MallaCurricularFormDefaults = Pick<NewMallaCurricular, 'id' | 'temas'>;

type MallaCurricularFormGroupContent = {
  id: FormControl<IMallaCurricular['id'] | NewMallaCurricular['id']>;
  titulo: FormControl<IMallaCurricular['titulo']>;
  nivel: FormControl<IMallaCurricular['nivel']>;
  temas: FormControl<IMallaCurricular['temas']>;
};

export type MallaCurricularFormGroup = FormGroup<MallaCurricularFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MallaCurricularFormService {
  createMallaCurricularFormGroup(mallaCurricular: MallaCurricularFormGroupInput = { id: null }): MallaCurricularFormGroup {
    const mallaCurricularRawValue = {
      ...this.getFormDefaults(),
      ...mallaCurricular,
    };
    return new FormGroup<MallaCurricularFormGroupContent>({
      id: new FormControl(
        { value: mallaCurricularRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      titulo: new FormControl(mallaCurricularRawValue.titulo, {
        validators: [Validators.required],
      }),
      nivel: new FormControl(mallaCurricularRawValue.nivel, {
        validators: [Validators.required],
      }),
      temas: new FormControl(mallaCurricularRawValue.temas ?? []),
    });
  }

  getMallaCurricular(form: MallaCurricularFormGroup): IMallaCurricular | NewMallaCurricular {
    return form.getRawValue() as IMallaCurricular | NewMallaCurricular;
  }

  resetForm(form: MallaCurricularFormGroup, mallaCurricular: MallaCurricularFormGroupInput): void {
    const mallaCurricularRawValue = { ...this.getFormDefaults(), ...mallaCurricular };
    form.reset(
      {
        ...mallaCurricularRawValue,
        id: { value: mallaCurricularRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MallaCurricularFormDefaults {
    return {
      id: null,
      temas: [],
    };
  }
}
