import { ISucursales, NewSucursales } from './sucursales.model';

export const sampleWithRequiredData: ISucursales = {
  id: 64468,
  nombreSucursal: 'Huerta 1080p relationships',
  numeroEstablecimiento: 60955,
};

export const sampleWithPartialData: ISucursales = {
  id: 64401,
  nombreSucursal: 'Pelota transmitter interface',
  direccion: 'nacional',
  numeroEstablecimiento: 12473,
};

export const sampleWithFullData: ISucursales = {
  id: 7777,
  nombreSucursal: 'global JSON',
  direccion: 'Poblado',
  numeroEstablecimiento: 91578,
};

export const sampleWithNewData: NewSucursales = {
  nombreSucursal: 'Subida',
  numeroEstablecimiento: 37356,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
