import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICursos, NewCursos } from '../cursos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICursos for edit and NewCursosFormGroupInput for create.
 */
type CursosFormGroupInput = ICursos | PartialWithRequiredKeyOf<NewCursos>;

type CursosFormDefaults = Pick<NewCursos, 'id'>;

type CursosFormGroupContent = {
  id: FormControl<ICursos['id'] | NewCursos['id']>;
  nombreCurso: FormControl<ICursos['nombreCurso']>;
};

export type CursosFormGroup = FormGroup<CursosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CursosFormService {
  createCursosFormGroup(cursos: CursosFormGroupInput = { id: null }): CursosFormGroup {
    const cursosRawValue = {
      ...this.getFormDefaults(),
      ...cursos,
    };
    return new FormGroup<CursosFormGroupContent>({
      id: new FormControl(
        { value: cursosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nombreCurso: new FormControl(cursosRawValue.nombreCurso),
    });
  }

  getCursos(form: CursosFormGroup): ICursos | NewCursos {
    return form.getRawValue() as ICursos | NewCursos;
  }

  resetForm(form: CursosFormGroup, cursos: CursosFormGroupInput): void {
    const cursosRawValue = { ...this.getFormDefaults(), ...cursos };
    form.reset(
      {
        ...cursosRawValue,
        id: { value: cursosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CursosFormDefaults {
    return {
      id: null,
    };
  }
}
