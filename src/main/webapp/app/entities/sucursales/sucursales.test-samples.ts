import { ISucursales, NewSucursales } from './sucursales.model';

export const sampleWithRequiredData: ISucursales = {
  id: 64468,
  nombreSucursal: 'Land 1080p relationships',
  numeroEstablecimiento: 60955,
};

export const sampleWithPartialData: ISucursales = {
  id: 64401,
  nombreSucursal: 'Ball transmitter interface',
  direccion: 'multi-tasking',
  numeroEstablecimiento: 12473,
};

export const sampleWithFullData: ISucursales = {
  id: 7777,
  nombreSucursal: 'full-range JSON',
  direccion: 'Port',
  numeroEstablecimiento: 91578,
};

export const sampleWithNewData: NewSucursales = {
  nombreSucursal: 'Unions',
  numeroEstablecimiento: 37356,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
