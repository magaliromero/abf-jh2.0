import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotaCreditoDetalle, NewNotaCreditoDetalle } from '../nota-credito-detalle.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotaCreditoDetalle for edit and NewNotaCreditoDetalleFormGroupInput for create.
 */
type NotaCreditoDetalleFormGroupInput = INotaCreditoDetalle | PartialWithRequiredKeyOf<NewNotaCreditoDetalle>;

type NotaCreditoDetalleFormDefaults = Pick<NewNotaCreditoDetalle, 'id'>;

type NotaCreditoDetalleFormGroupContent = {
  id: FormControl<INotaCreditoDetalle['id'] | NewNotaCreditoDetalle['id']>;
  cantidad: FormControl<INotaCreditoDetalle['cantidad']>;
  precioUnitario: FormControl<INotaCreditoDetalle['precioUnitario']>;
  subtotal: FormControl<INotaCreditoDetalle['subtotal']>;
  porcentajeIva: FormControl<INotaCreditoDetalle['porcentajeIva']>;
  valorPorcentaje: FormControl<INotaCreditoDetalle['valorPorcentaje']>;
  notaCredito: FormControl<INotaCreditoDetalle['notaCredito']>;
  producto: FormControl<INotaCreditoDetalle['producto']>;
};

export type NotaCreditoDetalleFormGroup = FormGroup<NotaCreditoDetalleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotaCreditoDetalleFormService {
  createNotaCreditoDetalleFormGroup(notaCreditoDetalle: NotaCreditoDetalleFormGroupInput = { id: null }): NotaCreditoDetalleFormGroup {
    const notaCreditoDetalleRawValue = {
      ...this.getFormDefaults(),
      ...notaCreditoDetalle,
    };
    return new FormGroup<NotaCreditoDetalleFormGroupContent>({
      id: new FormControl(
        { value: notaCreditoDetalleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cantidad: new FormControl(notaCreditoDetalleRawValue.cantidad),
      precioUnitario: new FormControl(notaCreditoDetalleRawValue.precioUnitario),
      subtotal: new FormControl(notaCreditoDetalleRawValue.subtotal),
      porcentajeIva: new FormControl(notaCreditoDetalleRawValue.porcentajeIva),
      valorPorcentaje: new FormControl(notaCreditoDetalleRawValue.valorPorcentaje),
      notaCredito: new FormControl(notaCreditoDetalleRawValue.notaCredito, {
        validators: [Validators.required],
      }),
      producto: new FormControl(notaCreditoDetalleRawValue.producto, {
        validators: [Validators.required],
      }),
    });
  }

  getNotaCreditoDetalle(form: NotaCreditoDetalleFormGroup): INotaCreditoDetalle | NewNotaCreditoDetalle {
    return form.getRawValue() as INotaCreditoDetalle | NewNotaCreditoDetalle;
  }

  resetForm(form: NotaCreditoDetalleFormGroup, notaCreditoDetalle: NotaCreditoDetalleFormGroupInput): void {
    const notaCreditoDetalleRawValue = { ...this.getFormDefaults(), ...notaCreditoDetalle };
    form.reset(
      {
        ...notaCreditoDetalleRawValue,
        id: { value: notaCreditoDetalleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): NotaCreditoDetalleFormDefaults {
    return {
      id: null,
    };
  }
}
