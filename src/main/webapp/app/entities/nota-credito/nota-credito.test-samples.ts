import dayjs from 'dayjs/esm';

import { INotaCredito, NewNotaCredito } from './nota-credito.model';

export const sampleWithRequiredData: INotaCredito = {
  id: 12164,
  fecha: dayjs('2023-10-30'),
  notaNro: 'voter',
  puntoExpedicion: 18674,
  sucursal: 16372,
  razonSocial: 'that orange for',
  ruc: 'gleaming',
  motivoEmision: 'DESCUENTO',
  total: 13385,
};

export const sampleWithPartialData: INotaCredito = {
  id: 18540,
  fecha: dayjs('2023-10-30'),
  notaNro: 'beneath',
  puntoExpedicion: 12345,
  sucursal: 11108,
  razonSocial: 'amidst',
  ruc: 'whose lest',
  direccion: 'realize trainer via',
  motivoEmision: 'OTRO',
  total: 28510,
};

export const sampleWithFullData: INotaCredito = {
  id: 18209,
  fecha: dayjs('2023-10-30'),
  notaNro: 'about',
  puntoExpedicion: 15527,
  sucursal: 7482,
  razonSocial: 'linen heavily',
  ruc: 'searchingly',
  direccion: 'hook hay',
  motivoEmision: 'OTRO',
  total: 30093,
};

export const sampleWithNewData: NewNotaCredito = {
  fecha: dayjs('2023-10-30'),
  notaNro: 'imaginary periodic',
  puntoExpedicion: 6870,
  sucursal: 11650,
  razonSocial: 'ugh',
  ruc: 'what boohoo garment',
  motivoEmision: 'ANULACION',
  total: 12939,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
