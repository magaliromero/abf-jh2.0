import dayjs from 'dayjs/esm';

import { IFacturas, NewFacturas } from './facturas.model';

export const sampleWithRequiredData: IFacturas = {
  id: 4707,
  fecha: dayjs('2023-05-31'),
  facturaNro: 'hence ha aha',
  timbrado: 4925,
  puntoExpedicion: 25572,
  sucursal: 28215,
  razonSocial: 'ugh junior against',
  ruc: 'failing so winged',
  condicionVenta: 'CONTADO',
  total: 4197,
};

export const sampleWithPartialData: IFacturas = {
  id: 20772,
  fecha: dayjs('2023-05-31'),
  facturaNro: 'keep authority',
  timbrado: 12097,
  puntoExpedicion: 25335,
  sucursal: 18835,
  razonSocial: 'why sedately',
  ruc: 'scud',
  condicionVenta: 'CONTADO',
  total: 7164,
};

export const sampleWithFullData: IFacturas = {
  id: 14098,
  fecha: dayjs('2023-05-31'),
  facturaNro: 'frenetically bitmap volley',
  timbrado: 28309,
  puntoExpedicion: 1303,
  sucursal: 8297,
  razonSocial: 'woot until',
  ruc: 'mmm mmm aromatic',
  condicionVenta: 'CONTADO',
  total: 27171,
};

export const sampleWithNewData: NewFacturas = {
  fecha: dayjs('2023-06-01'),
  facturaNro: 'never replica',
  timbrado: 19134,
  puntoExpedicion: 30454,
  sucursal: 7629,
  razonSocial: 'geez though gravy',
  ruc: 'unselfish energetically',
  condicionVenta: 'CONTADO',
  total: 14595,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
