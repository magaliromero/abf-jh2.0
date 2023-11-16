import dayjs from 'dayjs/esm';

import { CondicionVenta } from 'app/entities/enumerations/condicion-venta.model';
import { EstadosFacturas } from 'app/entities/enumerations/estados-facturas.model';

import { IFacturas, NewFacturas } from './facturas.model';

export const sampleWithRequiredData: IFacturas = {
  id: 29679,
  fecha: dayjs('2023-11-07'),
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
  id: 45383,
  fecha: dayjs('2023-11-07'),
  facturaNro: 'Regional Pantalones',
  timbrado: 94397,
  puntoExpedicion: 6241,
  sucursal: 48232,
  razonSocial: 'Contabilidad',
  ruc: 'mesh País',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 3999,
};

export const sampleWithFullData: IFacturas = {
  id: 97576,
  fecha: dayjs('2023-11-07'),
  facturaNro: 'Nacional',
  timbrado: 22694,
  puntoExpedicion: 82634,
  sucursal: 84802,
  razonSocial: 'productize',
  ruc: 'navigate initiatives CSS',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 82815,
  estado: EstadosFacturas['ANULADO'],
};

export const sampleWithNewData: NewFacturas = {
  fecha: dayjs('2023-11-07'),
  facturaNro: 'compuesto Gambia',
  timbrado: 59500,
  puntoExpedicion: 55248,
  sucursal: 87020,
  razonSocial: 'La Calidad',
  ruc: 'Hormigon ROI 24/365',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 47775,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
