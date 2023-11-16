import dayjs from 'dayjs/esm';

import { Motivo } from 'app/entities/enumerations/motivo.model';
import { EstadosFacturas } from 'app/entities/enumerations/estados-facturas.model';

import { INotaCredito, NewNotaCredito } from './nota-credito.model';

export const sampleWithRequiredData: INotaCredito = {
  id: 74146,
  fecha: dayjs('2023-11-15'),
  timbrado: 25200,
  notaNro: 'cultivate Futuro Tácticas',
  puntoExpedicion: 62351,
  sucursal: 92232,
  razonSocial: 'engineer',
  ruc: 'Calleja Danish Negro',
  motivoEmision: Motivo['OTRO'],
  total: 28926,
};

export const sampleWithPartialData: INotaCredito = {
  id: 55874,
  fecha: dayjs('2023-11-16'),
  timbrado: 75280,
  notaNro: 'Tunez',
  puntoExpedicion: 26067,
  sucursal: 95066,
  razonSocial: 'Lanka Rojo',
  ruc: 'Especialista backing Borders',
  direccion: 'Madera Rioja',
  motivoEmision: Motivo['DEVOLUCION'],
  estado: EstadosFacturas['ENTREGADO'],
  total: 74174,
};

export const sampleWithFullData: INotaCredito = {
  id: 5603,
  fecha: dayjs('2023-11-15'),
  timbrado: 69524,
  notaNro: 'withdrawal Verde',
  puntoExpedicion: 87659,
  sucursal: 8608,
  razonSocial: 'vortals',
  ruc: 'iniciativa',
  direccion: 'deploy',
  motivoEmision: Motivo['ANULACION'],
  estado: EstadosFacturas['PENDIENTE'],
  total: 78959,
};

export const sampleWithNewData: NewNotaCredito = {
  fecha: dayjs('2023-11-15'),
  timbrado: 66471,
  notaNro: 'mano',
  puntoExpedicion: 21107,
  sucursal: 46086,
  razonSocial: 'Exclusivo deposit Metal',
  ruc: 'generating Librería Singapur',
  motivoEmision: Motivo['DEVOLUCION'],
  total: 76527,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
