import { INotaCreditoDetalle, NewNotaCreditoDetalle } from './nota-credito-detalle.model';

export const sampleWithRequiredData: INotaCreditoDetalle = {
  id: 2439,
};

export const sampleWithPartialData: INotaCreditoDetalle = {
  id: 4341,
  cantidad: 8824,
  precioUnitario: 76094,
  subtotal: 96610,
  valorPorcentaje: 93088,
};

export const sampleWithFullData: INotaCreditoDetalle = {
  id: 56054,
  cantidad: 21880,
  precioUnitario: 37957,
  subtotal: 33894,
  porcentajeIva: 28069,
  valorPorcentaje: 28366,
};

export const sampleWithNewData: NewNotaCreditoDetalle = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
