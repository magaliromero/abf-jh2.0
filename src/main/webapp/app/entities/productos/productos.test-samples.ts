import { IProductos, NewProductos } from './productos.model';

export const sampleWithRequiredData: IProductos = {
  id: 16959,
  tipoProducto: 'SERVICIO',
  precioUnitario: 13682,
  porcentajeIva: 649,
  descripcion: 'integer oof',
};

export const sampleWithPartialData: IProductos = {
  id: 5778,
  tipoProducto: 'PRODUCTO',
  precioUnitario: 5576,
  porcentajeIva: 4317,
  descripcion: 'flicker',
};

export const sampleWithFullData: IProductos = {
  id: 23793,
  tipoProducto: 'PRODUCTO',
  precioUnitario: 23619,
  porcentajeIva: 2866,
  descripcion: 'eek',
};

export const sampleWithNewData: NewProductos = {
  tipoProducto: 'PRODUCTO',
  precioUnitario: 25018,
  porcentajeIva: 1574,
  descripcion: 'following aboard',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
