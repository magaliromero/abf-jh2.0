import dayjs from 'dayjs/esm';

import { IEvaluaciones, NewEvaluaciones } from './evaluaciones.model';

export const sampleWithRequiredData: IEvaluaciones = {
  id: 31287,
  nroEvaluacion: 26697,
  fecha: dayjs('2023-05-31'),
};

export const sampleWithPartialData: IEvaluaciones = {
  id: 30103,
  nroEvaluacion: 25178,
  fecha: dayjs('2023-06-01'),
};

export const sampleWithFullData: IEvaluaciones = {
  id: 8887,
  nroEvaluacion: 7026,
  fecha: dayjs('2023-06-01'),
};

export const sampleWithNewData: NewEvaluaciones = {
  nroEvaluacion: 6909,
  fecha: dayjs('2023-05-31'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
