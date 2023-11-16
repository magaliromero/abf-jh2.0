import dayjs from 'dayjs/esm';

import { ITimbrados, NewTimbrados } from './timbrados.model';

export const sampleWithRequiredData: ITimbrados = {
  id: 60634,
  numeroTimbrado: 59195,
  fechaInicio: dayjs('2023-11-15'),
  fechaFin: dayjs('2023-11-15'),
};

export const sampleWithPartialData: ITimbrados = {
  id: 22697,
  numeroTimbrado: 28380,
  fechaInicio: dayjs('2023-11-15'),
  fechaFin: dayjs('2023-11-16'),
};

export const sampleWithFullData: ITimbrados = {
  id: 4380,
  numeroTimbrado: 20427,
  fechaInicio: dayjs('2023-11-15'),
  fechaFin: dayjs('2023-11-15'),
};

export const sampleWithNewData: NewTimbrados = {
  numeroTimbrado: 22523,
  fechaInicio: dayjs('2023-11-15'),
  fechaFin: dayjs('2023-11-15'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
