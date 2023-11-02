import dayjs from 'dayjs/esm';

import { IMatricula, NewMatricula } from './matricula.model';

export const sampleWithRequiredData: IMatricula = {
  id: 4763,
  concepto: 'hm unto',
  monto: 4738,
  fechaInscripcion: dayjs('2023-05-31'),
  fechaInicio: dayjs('2023-06-01'),
  estado: 'ANULADO',
};

export const sampleWithPartialData: IMatricula = {
  id: 4497,
  concepto: 'insidious sans filthy',
  monto: 23073,
  fechaInscripcion: dayjs('2023-06-01'),
  fechaInicio: dayjs('2023-06-01'),
  estado: 'ANULADO',
};

export const sampleWithFullData: IMatricula = {
  id: 31546,
  concepto: 'fume enthusiastically',
  monto: 307,
  fechaInscripcion: dayjs('2023-06-01'),
  fechaInicio: dayjs('2023-06-01'),
  fechaPago: dayjs('2023-05-31'),
  estado: 'PAGADO',
};

export const sampleWithNewData: NewMatricula = {
  concepto: 'recklessly pfft ew',
  monto: 10768,
  fechaInscripcion: dayjs('2023-06-01'),
  fechaInicio: dayjs('2023-05-31'),
  estado: 'PAGADO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
