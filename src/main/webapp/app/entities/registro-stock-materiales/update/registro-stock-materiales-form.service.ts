import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRegistroStockMateriales, NewRegistroStockMateriales } from '../registro-stock-materiales.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRegistroStockMateriales for edit and NewRegistroStockMaterialesFormGroupInput for create.
 */
type RegistroStockMaterialesFormGroupInput = IRegistroStockMateriales | PartialWithRequiredKeyOf<NewRegistroStockMateriales>;

type RegistroStockMaterialesFormDefaults = Pick<NewRegistroStockMateriales, 'id'>;

type RegistroStockMaterialesFormGroupContent = {
  id: FormControl<IRegistroStockMateriales['id'] | NewRegistroStockMateriales['id']>;
  comentario: FormControl<IRegistroStockMateriales['comentario']>;
  cantidadInicial: FormControl<IRegistroStockMateriales['cantidadInicial']>;
  cantidadModificada: FormControl<IRegistroStockMateriales['cantidadModificada']>;
  fecha: FormControl<IRegistroStockMateriales['fecha']>;
  materiales: FormControl<IRegistroStockMateriales['materiales']>;
};

export type RegistroStockMaterialesFormGroup = FormGroup<RegistroStockMaterialesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RegistroStockMaterialesFormService {
  createRegistroStockMaterialesFormGroup(
    registroStockMateriales: RegistroStockMaterialesFormGroupInput = { id: null }
  ): RegistroStockMaterialesFormGroup {
    const registroStockMaterialesRawValue = {
      ...this.getFormDefaults(),
      ...registroStockMateriales,
    };
    return new FormGroup<RegistroStockMaterialesFormGroupContent>({
      id: new FormControl(
        { value: registroStockMaterialesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      comentario: new FormControl(registroStockMaterialesRawValue.comentario),
      cantidadInicial: new FormControl(registroStockMaterialesRawValue.cantidadInicial),
      cantidadModificada: new FormControl(registroStockMaterialesRawValue.cantidadModificada),
      fecha: new FormControl(registroStockMaterialesRawValue.fecha, {
        validators: [Validators.required],
      }),
      materiales: new FormControl(registroStockMaterialesRawValue.materiales),
    });
  }

  getRegistroStockMateriales(form: RegistroStockMaterialesFormGroup): IRegistroStockMateriales | NewRegistroStockMateriales {
    return form.getRawValue() as IRegistroStockMateriales | NewRegistroStockMateriales;
  }

  resetForm(form: RegistroStockMaterialesFormGroup, registroStockMateriales: RegistroStockMaterialesFormGroupInput): void {
    const registroStockMaterialesRawValue = { ...this.getFormDefaults(), ...registroStockMateriales };
    form.reset(
      {
        ...registroStockMaterialesRawValue,
        id: { value: registroStockMaterialesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RegistroStockMaterialesFormDefaults {
    return {
      id: null,
    };
  }
}
