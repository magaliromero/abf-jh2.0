import dayjs from 'dayjs/esm';

import { IPrestamos, NewPrestamos } from './prestamos.model';

export const sampleWithRequiredData: IPrestamos = {
  id: 9135,
  fechaPrestamo: dayjs('2023-03-13'),
  vigenciaPrestamo: 10614,
  fechaDevolucion: dayjs('2023-03-13'),
};

export const sampleWithPartialData: IPrestamos = {
  id: 94363,
  fechaPrestamo: dayjs('2023-03-13'),
  vigenciaPrestamo: 78952,
  fechaDevolucion: dayjs('2023-03-13'),
};

export const sampleWithFullData: IPrestamos = {
  id: 6291,
  fechaPrestamo: dayjs('2023-03-13'),
  vigenciaPrestamo: 99713,
  fechaDevolucion: dayjs('2023-03-13'),
};

export const sampleWithNewData: NewPrestamos = {
  fechaPrestamo: dayjs('2023-03-13'),
  vigenciaPrestamo: 56366,
  fechaDevolucion: dayjs('2023-03-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
