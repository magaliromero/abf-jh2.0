import dayjs from 'dayjs/esm';

import { IRegistroClases, NewRegistroClases } from './registro-clases.model';

export const sampleWithRequiredData: IRegistroClases = {
  id: 57702,
  fecha: dayjs('2023-11-07'),
  cantidadHoras: 61826,
};

export const sampleWithPartialData: IRegistroClases = {
  id: 95115,
  fecha: dayjs('2023-11-07'),
  cantidadHoras: 58298,
};

export const sampleWithFullData: IRegistroClases = {
  id: 48227,
  fecha: dayjs('2023-11-07'),
  cantidadHoras: 80841,
  asistenciaAlumno: false,
  pagado: false,
};

export const sampleWithNewData: NewRegistroClases = {
  fecha: dayjs('2023-11-07'),
  cantidadHoras: 93423,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
