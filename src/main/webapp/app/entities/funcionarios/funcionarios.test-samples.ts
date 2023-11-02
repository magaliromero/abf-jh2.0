import dayjs from 'dayjs/esm';

import { IFuncionarios, NewFuncionarios } from './funcionarios.model';

export const sampleWithRequiredData: IFuncionarios = {
  id: 21419,
  nombres: 'upwardly',
  apellidos: 'forenenst pro',
  nombreCompleto: 'oh barring stealthily',
  email: 'Milton.Stehr42@hotmail.com',
  telefono: 'careless',
  fechaNacimiento: dayjs('2023-06-01'),
  documento: 'miserably',
  estado: 'ACTIVO',
};

export const sampleWithPartialData: IFuncionarios = {
  id: 656,
  elo: 17241,
  nombres: 'romance',
  apellidos: 'copywriter reactant this',
  nombreCompleto: 'uh-huh',
  email: 'Charity_Doyle5@yahoo.com',
  telefono: 'whoever',
  fechaNacimiento: dayjs('2023-05-31'),
  documento: 'catsup',
  estado: 'INACTIVO',
  tipoFuncionario: 'FUNCIONARIOS',
};

export const sampleWithFullData: IFuncionarios = {
  id: 17838,
  elo: 28358,
  fideId: 27396,
  nombres: 'councilperson procedure or',
  apellidos: 'mountainous',
  nombreCompleto: 'shakily nor',
  email: 'Christiana8@yahoo.com',
  telefono: 'blaring cow',
  fechaNacimiento: dayjs('2023-06-01'),
  documento: 'superiority',
  estado: 'ACTIVO',
  tipoFuncionario: 'OTRO',
};

export const sampleWithNewData: NewFuncionarios = {
  nombres: 'boo an that',
  apellidos: 'softly reunify furthermore',
  nombreCompleto: 'narrate identical',
  email: 'Jason26@yahoo.com',
  telefono: 'ah',
  fechaNacimiento: dayjs('2023-05-31'),
  documento: 'huzzah',
  estado: 'ACTIVO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
