import { IMateriales, NewMateriales } from './materiales.model';

export const sampleWithRequiredData: IMateriales = {
  id: 28681,
  descripcion: 'apud',
};

export const sampleWithPartialData: IMateriales = {
  id: 30428,
  descripcion: 'um',
  cantidad: 7147,
};

export const sampleWithFullData: IMateriales = {
  id: 31081,
  descripcion: 'whoever',
  cantidad: 16624,
  cantidadEnPrestamo: 2987,
};

export const sampleWithNewData: NewMateriales = {
  descripcion: 'until fantasise insubstantial',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
