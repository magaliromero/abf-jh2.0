import { ICursos, NewCursos } from './cursos.model';

export const sampleWithRequiredData: ICursos = {
  id: 88953,
};

export const sampleWithPartialData: ICursos = {
  id: 56618,
  nombreCurso: 'Algodón payment bypassing',
};

export const sampleWithFullData: ICursos = {
  id: 16367,
  nombreCurso: 'Música methodologies',
};

export const sampleWithNewData: NewCursos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
