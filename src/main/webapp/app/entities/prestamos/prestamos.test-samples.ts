import dayjs from 'dayjs/esm';

import { IPrestamos, NewPrestamos } from './prestamos.model';

export const sampleWithRequiredData: IPrestamos = {
  id: 942,
  fechaPrestamo: dayjs('2023-05-31'),
  estado: 'PRESTADO',
};

export const sampleWithPartialData: IPrestamos = {
  id: 5753,
  fechaPrestamo: dayjs('2023-06-01'),
  fechaDevolucion: dayjs('2023-06-01'),
  estado: 'DEVUELTO',
};

export const sampleWithFullData: IPrestamos = {
  id: 5301,
  fechaPrestamo: dayjs('2023-06-01'),
  fechaDevolucion: dayjs('2023-06-01'),
  estado: 'DEVUELTO',
};

export const sampleWithNewData: NewPrestamos = {
  fechaPrestamo: dayjs('2023-05-31'),
  estado: 'DEVUELTO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
