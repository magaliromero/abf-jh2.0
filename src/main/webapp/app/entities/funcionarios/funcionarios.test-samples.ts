import dayjs from 'dayjs/esm';

import { EstadosPersona } from 'app/entities/enumerations/estados-persona.model';
import { TipoFuncionarios } from 'app/entities/enumerations/tipo-funcionarios.model';

import { IFuncionarios, NewFuncionarios } from './funcionarios.model';

export const sampleWithRequiredData: IFuncionarios = {
  id: 25530,
  nombres: 'Pequeño',
  apellidos: 'programming',
  nombreCompleto: 'Cine copying',
  email: 'MaraEugenia51@yahoo.com',
  telefono: 'deposit seamless',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'Credit Blanco',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithPartialData: IFuncionarios = {
  id: 66416,
  nombres: 'Polarizado',
  apellidos: 'utilize',
  nombreCompleto: 'Robusto',
  email: 'Octavio.Gaitn94@hotmail.com',
  telefono: 'orchestrate y Configurable',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'soporte Avon',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithFullData: IFuncionarios = {
  id: 2032,
  nombres: 'Total',
  apellidos: 'wireless',
  nombreCompleto: 'actitud Ordenador',
  email: 'Ramona17@hotmail.com',
  telefono: 'Cambridgeshire',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'Global Acero',
  estado: EstadosPersona['ACTIVO'],
  tipoFuncionario: TipoFuncionarios['FUNCIONARIOS'],
  elo: 24239,
  fideId: 61935,
};

export const sampleWithNewData: NewFuncionarios = {
  nombres: 'indexing Comunidad',
  apellidos: 'wireless Central utilize',
  nombreCompleto: 'auxiliary Nigeria Metal',
  email: 'Rafael_Maldonado79@hotmail.com',
  telefono: 'Berkshire quantifying',
  fechaNacimiento: dayjs('2023-11-07'),
  documento: 'Gris metrics facilitate',
  estado: EstadosPersona['ACTIVO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
