import { Niveles } from 'app/entities/enumerations/niveles.model';

import { IMallaCurricular, NewMallaCurricular } from './malla-curricular.model';

export const sampleWithRequiredData: IMallaCurricular = {
  id: 84891,
  titulo: 'haptic index synthesize',
  nivel: Niveles['TODOS'],
};

export const sampleWithPartialData: IMallaCurricular = {
  id: 89125,
  titulo: 'application',
  nivel: Niveles['AVANZADO'],
};

export const sampleWithFullData: IMallaCurricular = {
  id: 91646,
  titulo: 'empresa asimétrica',
  nivel: Niveles['TODOS'],
};

export const sampleWithNewData: NewMallaCurricular = {
  titulo: 'streamline',
  nivel: Niveles['TODOS'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
