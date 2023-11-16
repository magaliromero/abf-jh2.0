import dayjs from 'dayjs/esm';

import { IRegistroStockMateriales, NewRegistroStockMateriales } from './registro-stock-materiales.model';

export const sampleWithRequiredData: IRegistroStockMateriales = {
  id: 35303,
  fecha: dayjs('2023-11-16'),
};

export const sampleWithPartialData: IRegistroStockMateriales = {
  id: 43839,
  cantidadInicial: 72628,
  fecha: dayjs('2023-11-15'),
};

export const sampleWithFullData: IRegistroStockMateriales = {
  id: 20598,
  comentario: 'Director orchestrate',
  cantidadInicial: 70917,
  cantidadModificada: 96776,
  fecha: dayjs('2023-11-15'),
};

export const sampleWithNewData: NewRegistroStockMateriales = {
  fecha: dayjs('2023-11-15'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
