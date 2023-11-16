import dayjs from 'dayjs/esm';

import { Niveles } from 'app/entities/enumerations/niveles.model';

import { ICursos, NewCursos } from './cursos.model';

export const sampleWithRequiredData: ICursos = {
  id: 88953,
  nombreCurso: 'País channels Cuba',
  descripcion: 'back-end',
  nivel: Niveles['PREAJEDREZ'],
};

export const sampleWithPartialData: ICursos = {
  id: 96032,
  nombreCurso: 'dot-com Operaciones',
  descripcion: 'Castilla',
  fechaInicio: dayjs('2023-11-15'),
  nivel: Niveles['INICIAL'],
};

export const sampleWithFullData: ICursos = {
  id: 78666,
  nombreCurso: 'microchip',
  descripcion: 'solid Pescado',
  fechaInicio: dayjs('2023-11-15'),
  fechaFin: dayjs('2023-11-15'),
  cantidadClases: 82546,
  nivel: Niveles['INICIAL'],
};

export const sampleWithNewData: NewCursos = {
  nombreCurso: 'Violeta',
  descripcion: 'Genérico digital Morado',
  nivel: Niveles['INICIAL'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
