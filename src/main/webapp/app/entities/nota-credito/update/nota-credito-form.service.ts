import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotaCredito, NewNotaCredito } from '../nota-credito.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotaCredito for edit and NewNotaCreditoFormGroupInput for create.
 */
type NotaCreditoFormGroupInput = INotaCredito | PartialWithRequiredKeyOf<NewNotaCredito>;

type NotaCreditoFormDefaults = Pick<NewNotaCredito, 'id'>;

type NotaCreditoFormGroupContent = {
  id: FormControl<INotaCredito['id'] | NewNotaCredito['id']>;
  fecha: FormControl<INotaCredito['fecha']>;
  timbrado: FormControl<INotaCredito['timbrado']>;
  notaNro: FormControl<INotaCredito['notaNro']>;
  puntoExpedicion: FormControl<INotaCredito['puntoExpedicion']>;
  sucursal: FormControl<INotaCredito['sucursal']>;
  razonSocial: FormControl<INotaCredito['razonSocial']>;
  ruc: FormControl<INotaCredito['ruc']>;
  direccion: FormControl<INotaCredito['direccion']>;
  motivoEmision: FormControl<INotaCredito['motivoEmision']>;
  estado: FormControl<INotaCredito['estado']>;
  total: FormControl<INotaCredito['total']>;
  facturas: FormControl<INotaCredito['facturas']>;
};

export type NotaCreditoFormGroup = FormGroup<NotaCreditoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotaCreditoFormService {
  createNotaCreditoFormGroup(notaCredito: NotaCreditoFormGroupInput = { id: null }): NotaCreditoFormGroup {
    const notaCreditoRawValue = {
      ...this.getFormDefaults(),
      ...notaCredito,
    };
    return new FormGroup<NotaCreditoFormGroupContent>({
      id: new FormControl(
        { value: notaCreditoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fecha: new FormControl(notaCreditoRawValue.fecha, {
        validators: [Validators.required],
      }),
      timbrado: new FormControl(notaCreditoRawValue.timbrado, {
        validators: [Validators.required],
      }),
      notaNro: new FormControl(notaCreditoRawValue.notaNro, {
        validators: [Validators.required],
      }),
      puntoExpedicion: new FormControl(notaCreditoRawValue.puntoExpedicion, {
        validators: [Validators.required],
      }),
      sucursal: new FormControl(notaCreditoRawValue.sucursal, {
        validators: [Validators.required],
      }),
      razonSocial: new FormControl(notaCreditoRawValue.razonSocial, {
        validators: [Validators.required],
      }),
      ruc: new FormControl(notaCreditoRawValue.ruc, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(notaCreditoRawValue.direccion),
      motivoEmision: new FormControl(notaCreditoRawValue.motivoEmision, {
        validators: [Validators.required],
      }),
      estado: new FormControl(notaCreditoRawValue.estado),
      total: new FormControl(notaCreditoRawValue.total, {
        validators: [Validators.required],
      }),
      facturas: new FormControl(notaCreditoRawValue.facturas, {
        validators: [Validators.required],
      }),
    });
  }

  getNotaCredito(form: NotaCreditoFormGroup): INotaCredito | NewNotaCredito {
    return form.getRawValue() as INotaCredito | NewNotaCredito;
  }

  resetForm(form: NotaCreditoFormGroup, notaCredito: NotaCreditoFormGroupInput): void {
    const notaCreditoRawValue = { ...this.getFormDefaults(), ...notaCredito };
    form.reset(
      {
        ...notaCreditoRawValue,
        id: { value: notaCreditoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotaCreditoFormDefaults {
    return {
      id: null,
    };
  }
}
