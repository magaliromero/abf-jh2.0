import { INotaCreditoDetalle, NewNotaCreditoDetalle } from './nota-credito-detalle.model';

export const sampleWithRequiredData: INotaCreditoDetalle = {
  id: 13241,
};

export const sampleWithPartialData: INotaCreditoDetalle = {
  id: 13961,
  cantidad: 23805,
  subtotal: 32607,
  valorPorcentaje: 11627,
};

export const sampleWithFullData: INotaCreditoDetalle = {
  id: 10188,
  cantidad: 26517,
  precioUnitario: 2481,
  subtotal: 27948,
  porcentajeIva: 20043,
  valorPorcentaje: 14923,
};

export const sampleWithNewData: NewNotaCreditoDetalle = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
