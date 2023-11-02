import dayjs from 'dayjs/esm';

import { ITimbrados, NewTimbrados } from './timbrados.model';

export const sampleWithRequiredData: ITimbrados = {
  id: 28838,
  numeroTimbrado: 18799,
  fechaInicio: dayjs('2023-08-12'),
  fechaFin: dayjs('2023-08-12'),
};

export const sampleWithPartialData: ITimbrados = {
  id: 22030,
  numeroTimbrado: 10,
  fechaInicio: dayjs('2023-08-12'),
  fechaFin: dayjs('2023-08-11'),
};

export const sampleWithFullData: ITimbrados = {
  id: 3512,
  numeroTimbrado: 19738,
  fechaInicio: dayjs('2023-08-11'),
  fechaFin: dayjs('2023-08-11'),
};

export const sampleWithNewData: NewTimbrados = {
  numeroTimbrado: 30879,
  fechaInicio: dayjs('2023-08-12'),
  fechaFin: dayjs('2023-08-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
