import dayjs from 'dayjs/esm';

import { IPagos, NewPagos } from './pagos.model';

export const sampleWithRequiredData: IPagos = {
  id: 35750,
  montoPago: 69414,
  montoInicial: 36645,
  saldo: 2075,
  fechaRegistro: dayjs('2023-03-13'),
  fechaPago: dayjs('2023-03-13'),
  tipoPago: 'JSON Especialista',
  descripcion: 'Market Regional Distribuido',
  idUsuarioRegistro: 29203,
};

export const sampleWithPartialData: IPagos = {
  id: 24366,
  montoPago: 47263,
  montoInicial: 47134,
  saldo: 48664,
  fechaRegistro: dayjs('2023-03-13'),
  fechaPago: dayjs('2023-03-13'),
  tipoPago: 'Castilla copy Cantabria',
  descripcion: 'Buckinghamshire',
  idUsuarioRegistro: 58299,
};

export const sampleWithFullData: IPagos = {
  id: 31611,
  montoPago: 20140,
  montoInicial: 95078,
  saldo: 68388,
  fechaRegistro: dayjs('2023-03-13'),
  fechaPago: dayjs('2023-03-13'),
  tipoPago: 'networks Tenge',
  descripcion: 'Guantes Mercados',
  idUsuarioRegistro: 45828,
};

export const sampleWithNewData: NewPagos = {
  montoPago: 38192,
  montoInicial: 70073,
  saldo: 79560,
  fechaRegistro: dayjs('2023-03-13'),
  fechaPago: dayjs('2023-03-13'),
  tipoPago: 'Azerbayán Account envisioneer',
  descripcion: 'Camiseta óptima transmitting',
  idUsuarioRegistro: 84982,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
