import dayjs from 'dayjs/esm';

import { ITorneos, NewTorneos } from './torneos.model';

export const sampleWithRequiredData: ITorneos = {
  id: 72448,
  nombreTorneo: 'state',
  fechaInicio: dayjs('2023-03-12'),
  fechaFin: dayjs('2023-03-13'),
  lugar: 'Zapatos index array',
  torneoEvaluado: false,
  federado: true,
};

export const sampleWithPartialData: ITorneos = {
  id: 38900,
  nombreTorneo: 'invoice',
  fechaInicio: dayjs('2023-03-13'),
  fechaFin: dayjs('2023-03-13'),
  lugar: 'calculating',
  torneoEvaluado: false,
  federado: true,
};

export const sampleWithFullData: ITorneos = {
  id: 83450,
  nombreTorneo: 'Guapa bandwidth Municipio',
  fechaInicio: dayjs('2023-03-13'),
  fechaFin: dayjs('2023-03-13'),
  lugar: 'next-generation',
  tiempo: 'Orgánico',
  tipoTorneo: 'Genérico Cine',
  torneoEvaluado: true,
  federado: true,
};

export const sampleWithNewData: NewTorneos = {
  nombreTorneo: 'Música Deportes',
  fechaInicio: dayjs('2023-03-13'),
  fechaFin: dayjs('2023-03-13'),
  lugar: 'facilitate Singapore',
  torneoEvaluado: false,
  federado: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
