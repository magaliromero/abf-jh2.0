import dayjs from 'dayjs/esm';

import { IEvaluaciones, NewEvaluaciones } from './evaluaciones.model';

export const sampleWithRequiredData: IEvaluaciones = {
  id: 68068,
  tipoEvaluacion: 'Avon CSS Global',
  fecha: dayjs('2023-03-13'),
};

export const sampleWithPartialData: IEvaluaciones = {
  id: 66628,
  tipoEvaluacion: 'connecting acceso channels',
  idExamen: 23897,
  fecha: dayjs('2023-03-13'),
  puntosLogrados: 21753,
};

export const sampleWithFullData: IEvaluaciones = {
  id: 34615,
  tipoEvaluacion: 'program',
  idExamen: 57872,
  idActa: 30764,
  fecha: dayjs('2023-03-13'),
  puntosLogrados: 63170,
  porcentaje: 35386,
  comentarios: 'Franc',
};

export const sampleWithNewData: NewEvaluaciones = {
  tipoEvaluacion: 'JBOD Fácil habilidad',
  fecha: dayjs('2023-03-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
