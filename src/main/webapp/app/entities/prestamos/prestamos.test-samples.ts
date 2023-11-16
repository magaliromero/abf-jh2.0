import dayjs from 'dayjs/esm';

import { EstadosPrestamos } from 'app/entities/enumerations/estados-prestamos.model';

import { IPrestamos, NewPrestamos } from './prestamos.model';

export const sampleWithRequiredData: IPrestamos = {
  id: 9135,
  fechaPrestamo: dayjs('2023-11-16'),
  estado: EstadosPrestamos['DEVUELTO'],
};

export const sampleWithPartialData: IPrestamos = {
  id: 50234,
  fechaPrestamo: dayjs('2023-11-15'),
  estado: EstadosPrestamos['VENCIDO'],
  observaciones: 'Región',
};

export const sampleWithFullData: IPrestamos = {
  id: 60787,
  fechaPrestamo: dayjs('2023-11-15'),
  fechaDevolucion: dayjs('2023-11-15'),
  estado: EstadosPrestamos['DEVUELTO'],
  observaciones: 'UIC-Franc Genérico',
};

export const sampleWithNewData: NewPrestamos = {
  fechaPrestamo: dayjs('2023-11-15'),
  estado: EstadosPrestamos['VENCIDO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
