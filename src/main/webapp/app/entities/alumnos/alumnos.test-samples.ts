import dayjs from 'dayjs/esm';

import { EstadosPersona } from 'app/entities/enumerations/estados-persona.model';

import { IAlumnos, NewAlumnos } from './alumnos.model';

export const sampleWithRequiredData: IAlumnos = {
  id: 67399,
  nombres: 'Guapo',
  apellidos: 'Cambridgeshire',
  nombreCompleto: 'Orígenes Directo Heredado',
  telefono: 'Galicia Moda mobile',
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'groupware magnetic middleware',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithPartialData: IAlumnos = {
  id: 33002,
  elo: 30108,
  nombres: 'eyeballs',
  apellidos: 'Papelería Nacional',
  nombreCompleto: 'AI transmitting',
  telefono: 'Negro País',
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'enfoque Kenyan Malta',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithFullData: IAlumnos = {
  id: 74829,
  elo: 70893,
  fideId: 4894,
  nombres: 'Raton Bicicleta e-business',
  apellidos: 'Extramuros Pequeño',
  nombreCompleto: 'channels HTTP Humano',
  email: 'MaraEugenia30@gmail.com',
  telefono: 'payment Verde',
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'Madera Arquitecto e-commerce',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithNewData: NewAlumnos = {
  nombres: 'Algodón auxiliary',
  apellidos: 'generate Bedfordshire Morado',
  nombreCompleto: 'Account',
  telefono: 'cross-platform',
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'Auto Market',
  estado: EstadosPersona['ACTIVO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
