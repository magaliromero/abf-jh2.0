import dayjs from 'dayjs/esm';

import { EstadosPersona } from 'app/entities/enumerations/estados-persona.model';

import { IAlumnos, NewAlumnos } from './alumnos.model';

export const sampleWithRequiredData: IAlumnos = {
  id: 67399,
  nombres: 'Guapo',
  apellidos: 'Cambridgeshire',
  nombreCompleto: 'Orígenes Directo Heredado',
  telefono: 'Galicia Moda mobile',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'groupware magnetic middleware',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithPartialData: IAlumnos = {
  id: 33002,
  nombres: 'Camiseta',
  apellidos: 'Account Units Regional',
  nombreCompleto: 'applications Polarizado',
  email: 'Javier.Montenegro55@gmail.com',
  telefono: 'enfoque Kenyan Malta',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'protocol Raton Bicicleta',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithFullData: IAlumnos = {
  id: 17171,
  nombres: 'Lado Acero sistema',
  apellidos: 'transmitting',
  nombreCompleto: 'Humano Morado Granito',
  email: 'Javier.Regalado85@gmail.com',
  telefono: 'Chad',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'Extramuros Checking innovative',
  estado: EstadosPersona['ACTIVO'],
  elo: 78425,
  fideId: 3922,
};

export const sampleWithNewData: NewAlumnos = {
  nombres: 'synthesizing sensor',
  apellidos: 'Morado',
  nombreCompleto: 'Account',
  telefono: 'cross-platform',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'Auto Market',
  estado: EstadosPersona['ACTIVO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
