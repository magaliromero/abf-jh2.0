import dayjs from 'dayjs/esm';

import { IAlumnos, NewAlumnos } from './alumnos.model';

export const sampleWithRequiredData: IAlumnos = {
  id: 5673,
  nombres: 'freeload multicast greatly',
  apellidos: 'um',
  nombreCompleto: 'dine',
  telefono: 'even',
  fechaNacimiento: dayjs('2023-05-31'),
  documento: 'deaden interface',
  estado: 'ACTIVO',
};

export const sampleWithPartialData: IAlumnos = {
  id: 13758,
  elo: 27499,
  nombres: 'gee',
  apellidos: 'inside stingy in',
  nombreCompleto: 'gregarious gosh',
  email: 'Moises5@hotmail.com',
  telefono: 'well-groomed',
  fechaNacimiento: dayjs('2023-05-31'),
  documento: 'atop',
  estado: 'ACTIVO',
};

export const sampleWithFullData: IAlumnos = {
  id: 23949,
  elo: 8629,
  fideId: 1144,
  nombres: 'vice meh',
  apellidos: 'gee',
  nombreCompleto: 'grove',
  email: 'Percival.VonRueden@yahoo.com',
  telefono: 'firebomb suite',
  fechaNacimiento: dayjs('2023-05-31'),
  documento: 'searchingly psst aboard',
  estado: 'INACTIVO',
};

export const sampleWithNewData: NewAlumnos = {
  nombres: 'at or solemnly',
  apellidos: 'whose',
  nombreCompleto: 'shakily',
  telefono: 'buoyant',
  fechaNacimiento: dayjs('2023-06-01'),
  documento: 'aha frugal ephemeris',
  estado: 'INACTIVO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
