import dayjs from 'dayjs/esm';

import { IRegistroClases, NewRegistroClases } from './registro-clases.model';

export const sampleWithRequiredData: IRegistroClases = {
  id: 9352,
  fecha: dayjs('2023-06-01'),
  cantidadHoras: 5368,
};

export const sampleWithPartialData: IRegistroClases = {
  id: 6819,
  fecha: dayjs('2023-05-31'),
  cantidadHoras: 4332,
  asistenciaAlumno: false,
};

export const sampleWithFullData: IRegistroClases = {
  id: 4032,
  fecha: dayjs('2023-06-01'),
  cantidadHoras: 3126,
  asistenciaAlumno: false,
};

export const sampleWithNewData: NewRegistroClases = {
  fecha: dayjs('2023-05-31'),
  cantidadHoras: 22918,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
