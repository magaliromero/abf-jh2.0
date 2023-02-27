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
  fechaNacimiento: dayjs('2023-02-27'),
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
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'soporte Avon',
  estado: EstadosPersona['ACTIVO'],
};

export const sampleWithFullData: IFuncionarios = {
  id: 2032,
  elo: 28354,
  fideId: 25451,
  nombres: 'bypassing capacitor actitud',
  apellidos: 'payment',
  nombreCompleto: 'Agente Descentralizado Cambridgeshire',
  email: 'Ana_Torres@hotmail.com',
  telefono: 'Acero',
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'communities',
  estado: EstadosPersona['INACTIVO'],
  tipoFuncionario: TipoFuncionarios['OTRO'],
};

export const sampleWithNewData: NewFuncionarios = {
  nombres: 'interfaz radical',
  apellidos: 'Agente Castilla-La Pound',
  nombreCompleto: 'Toallas',
  email: 'Federico_Pagan@yahoo.com',
  telefono: 'Cambridgeshire online',
  fechaNacimiento: dayjs('2023-02-27'),
  documento: 'quantifying Pantalones',
  estado: EstadosPersona['ACTIVO'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
