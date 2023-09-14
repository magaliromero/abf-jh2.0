import dayjs from 'dayjs/esm';

import { CondicionVenta } from 'app/entities/enumerations/condicion-venta.model';

import { IFacturas, NewFacturas } from './facturas.model';

export const sampleWithRequiredData: IFacturas = {
  id: 29679,
  fecha: dayjs('2023-06-01'),
  facturaNro: 'explicit circuit',
  timbrado: 61190,
  puntoExpedicion: 31438,
  sucursal: 69517,
  razonSocial: 'Turkey Solomon',
  ruc: 'Home Valley',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 19828,
};

export const sampleWithPartialData: IFacturas = {
  id: 33085,
  fecha: dayjs('2023-06-01'),
  facturaNro: 'functionalities Yuan',
  timbrado: 4130,
  puntoExpedicion: 94397,
  sucursal: 6241,
  razonSocial: 'User-centric connect',
  ruc: 'Organic Dam',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 66134,
};

export const sampleWithFullData: IFacturas = {
  id: 13455,
  fecha: dayjs('2023-05-31'),
  facturaNro: 'compress bypassing',
  timbrado: 95856,
  puntoExpedicion: 85696,
  sucursal: 76002,
  razonSocial: 'facilitate Bacon tan',
  ruc: 'Licensed Hat distributed',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 50823,
};

export const sampleWithNewData: NewFacturas = {
  fecha: dayjs('2023-06-01'),
  facturaNro: 'Fish Frozen ROI',
  timbrado: 34930,
  puntoExpedicion: 38516,
  sucursal: 12789,
  razonSocial: 'Terrace',
  ruc: 'magnetic',
  condicionVenta: CondicionVenta['CONTADO'],
  total: 2091,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
