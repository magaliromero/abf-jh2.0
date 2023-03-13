import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEvaluaciones, NewEvaluaciones } from '../evaluaciones.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEvaluaciones for edit and NewEvaluacionesFormGroupInput for create.
 */
type EvaluacionesFormGroupInput = IEvaluaciones | PartialWithRequiredKeyOf<NewEvaluaciones>;

type EvaluacionesFormDefaults = Pick<NewEvaluaciones, 'id'>;

type EvaluacionesFormGroupContent = {
  id: FormControl<IEvaluaciones['id'] | NewEvaluaciones['id']>;
  tipoEvaluacion: FormControl<IEvaluaciones['tipoEvaluacion']>;
  idExamen: FormControl<IEvaluaciones['idExamen']>;
  idActa: FormControl<IEvaluaciones['idActa']>;
  fecha: FormControl<IEvaluaciones['fecha']>;
  puntosLogrados: FormControl<IEvaluaciones['puntosLogrados']>;
  porcentaje: FormControl<IEvaluaciones['porcentaje']>;
  comentarios: FormControl<IEvaluaciones['comentarios']>;
  alumnos: FormControl<IEvaluaciones['alumnos']>;
};

export type EvaluacionesFormGroup = FormGroup<EvaluacionesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EvaluacionesFormService {
  createEvaluacionesFormGroup(evaluaciones: EvaluacionesFormGroupInput = { id: null }): EvaluacionesFormGroup {
    const evaluacionesRawValue = {
      ...this.getFormDefaults(),
      ...evaluaciones,
    };
    return new FormGroup<EvaluacionesFormGroupContent>({
      id: new FormControl(
        { value: evaluacionesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      tipoEvaluacion: new FormControl(evaluacionesRawValue.tipoEvaluacion, {
        validators: [Validators.required],
      }),
      idExamen: new FormControl(evaluacionesRawValue.idExamen),
      idActa: new FormControl(evaluacionesRawValue.idActa),
      fecha: new FormControl(evaluacionesRawValue.fecha, {
        validators: [Validators.required],
      }),
      puntosLogrados: new FormControl(evaluacionesRawValue.puntosLogrados),
      porcentaje: new FormControl(evaluacionesRawValue.porcentaje),
      comentarios: new FormControl(evaluacionesRawValue.comentarios),
      alumnos: new FormControl(evaluacionesRawValue.alumnos, {
        validators: [Validators.required],
      }),
    });
  }

  getEvaluaciones(form: EvaluacionesFormGroup): IEvaluaciones | NewEvaluaciones {
    return form.getRawValue() as IEvaluaciones | NewEvaluaciones;
  }

  resetForm(form: EvaluacionesFormGroup, evaluaciones: EvaluacionesFormGroupInput): void {
    const evaluacionesRawValue = { ...this.getFormDefaults(), ...evaluaciones };
    form.reset(
      {
        ...evaluacionesRawValue,
        id: { value: evaluacionesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EvaluacionesFormDefaults {
    return {
      id: null,
    };
  }
}
