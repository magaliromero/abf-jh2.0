import { IEvaluacionesDetalle, NewEvaluacionesDetalle } from './evaluaciones-detalle.model';

export const sampleWithRequiredData: IEvaluacionesDetalle = {
  id: 3287,
  comentarios: 'viciously shine',
  puntaje: 2616,
};

export const sampleWithPartialData: IEvaluacionesDetalle = {
  id: 1007,
  comentarios: 'silhouette catnap',
  puntaje: 14090,
};

export const sampleWithFullData: IEvaluacionesDetalle = {
  id: 26414,
  comentarios: 'when',
  puntaje: 23318,
};

export const sampleWithNewData: NewEvaluacionesDetalle = {
  comentarios: 'save rap urgently',
  puntaje: 30643,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
