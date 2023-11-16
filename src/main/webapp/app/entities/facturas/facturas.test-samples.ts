import dayjs from 'dayjs/esm';

import { CondicionVenta } from 'app/entities/enumerations/condicion-venta.model';
import { EstadosFacturas } from 'app/entities/enumerations/estados-facturas.model';

import { IFacturas, NewFacturas } from './facturas.model';

export const sampleWithRequiredData: IFacturas = {
  id: 29679,
  fecha: dayjs('2023-11-15'),
  facturaNro: 'a circuit',
  timbrado: 61190,
  puntoExpedicion: 31438,
  sucursal: 69517,
  razonSocial: 'Tobago Solomon',
  ruc: 'Home Terrenos',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 19828,
};

export const sampleWithPartialData: IFacturas = {
  id: 55207,
  fecha: dayjs('2023-11-15'),
  facturaNro: 'visualize vertical Salchichas',
  timbrado: 74568,
  puntoExpedicion: 45930,
  sucursal: 39300,
  razonSocial: 'Opcional Calleja',
  ruc: 'deposit enterprise transmitting',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 95856,
};

export const sampleWithFullData: IFacturas = {
  id: 85696,
  fecha: dayjs('2023-11-15'),
  facturaNro: 'facilitate Bacon Blanco',
  timbrado: 82815,
  puntoExpedicion: 5477,
  sucursal: 78940,
  razonSocial: 'compuesto Gambia',
  ruc: 'Siria La',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 66723,
  estado: EstadosFacturas['PENDIENTE'],
  poseeNC: true,
};

export const sampleWithNewData: NewFacturas = {
  fecha: dayjs('2023-11-15'),
  facturaNro: 'Investment Gris Acero',
  timbrado: 85377,
  puntoExpedicion: 71366,
  sucursal: 1954,
  razonSocial: 'applications Bricolaje',
  ruc: 'navigating bypass',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 37661,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
