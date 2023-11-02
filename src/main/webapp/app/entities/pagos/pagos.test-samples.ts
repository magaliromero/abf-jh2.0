import dayjs from 'dayjs/esm';

import { IPagos, NewPagos } from './pagos.model';

export const sampleWithRequiredData: IPagos = {
  id: 14636,
  fecha: dayjs('2023-06-01'),
  total: 11435,
  cantidadHoras: 22855,
};

export const sampleWithPartialData: IPagos = {
  id: 13727,
  fecha: dayjs('2023-06-01'),
  total: 31501,
  cantidadHoras: 4717,
};

export const sampleWithFullData: IPagos = {
  id: 21193,
  fecha: dayjs('2023-05-31'),
  total: 25586,
  cantidadHoras: 29597,
};

export const sampleWithNewData: NewPagos = {
  fecha: dayjs('2023-05-31'),
  total: 23910,
  cantidadHoras: 12814,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
