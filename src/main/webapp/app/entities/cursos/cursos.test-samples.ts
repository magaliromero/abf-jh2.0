import dayjs from 'dayjs/esm';

import { ICursos, NewCursos } from './cursos.model';

export const sampleWithRequiredData: ICursos = {
  id: 21537,
  nombreCurso: 'wheel',
  descripcion: 'meh finally ectodermal',
  nivel: 'INICIAL',
};

export const sampleWithPartialData: ICursos = {
  id: 29579,
  nombreCurso: 'classroom',
  descripcion: 'paraphrase maintain',
  fechaInicio: dayjs('2023-06-01'),
  nivel: 'INICIAL',
};

export const sampleWithFullData: ICursos = {
  id: 18236,
  nombreCurso: 'loose as',
  descripcion: 'pish concerned',
  fechaInicio: dayjs('2023-06-01'),
  fechaFin: dayjs('2023-05-31'),
  cantidadClases: 11045,
  nivel: 'PRINCIPIANTE',
};

export const sampleWithNewData: NewCursos = {
  nombreCurso: 'flatline violently',
  descripcion: 'referendum',
  nivel: 'AVANZADO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
