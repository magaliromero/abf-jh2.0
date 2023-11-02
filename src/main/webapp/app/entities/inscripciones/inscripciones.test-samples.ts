import dayjs from 'dayjs/esm';

import { IInscripciones, NewInscripciones } from './inscripciones.model';

export const sampleWithRequiredData: IInscripciones = {
  id: 9958,
};

export const sampleWithPartialData: IInscripciones = {
  id: 28610,
};

export const sampleWithFullData: IInscripciones = {
  id: 6502,
  fechaInscripcion: dayjs('2023-05-31'),
};

export const sampleWithNewData: NewInscripciones = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
