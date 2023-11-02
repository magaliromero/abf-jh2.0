import { ISucursales, NewSucursales } from './sucursales.model';

export const sampleWithRequiredData: ISucursales = {
  id: 510,
  nombreSucursal: 'down',
  numeroEstablecimiento: 26919,
};

export const sampleWithPartialData: ISucursales = {
  id: 4590,
  nombreSucursal: 'tugboat delight regarding',
  direccion: 'woot',
  numeroEstablecimiento: 7114,
};

export const sampleWithFullData: ISucursales = {
  id: 675,
  nombreSucursal: 'yuck',
  direccion: 'yum cruelly mileage',
  numeroEstablecimiento: 24199,
};

export const sampleWithNewData: NewSucursales = {
  nombreSucursal: 'inasmuch',
  numeroEstablecimiento: 24448,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
