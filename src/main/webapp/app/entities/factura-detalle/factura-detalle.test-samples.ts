import { IFacturaDetalle, NewFacturaDetalle } from './factura-detalle.model';

export const sampleWithRequiredData: IFacturaDetalle = {
  id: 11660,
};

export const sampleWithPartialData: IFacturaDetalle = {
  id: 13638,
  precioUnitario: 19286,
  subtotal: 16481,
};

export const sampleWithFullData: IFacturaDetalle = {
  id: 25928,
  cantidad: 3939,
  precioUnitario: 20413,
  subtotal: 32182,
  porcentajeIva: 26284,
  valorPorcentaje: 27430,
};

export const sampleWithNewData: NewFacturaDetalle = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
