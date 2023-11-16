import { IMateriales, NewMateriales } from './materiales.model';

export const sampleWithRequiredData: IMateriales = {
  id: 51682,
  descripcion: 'Hecho Avanzado withdrawal',
};

export const sampleWithPartialData: IMateriales = {
  id: 90659,
  descripcion: 'Técnico hack Bricolaje',
  cantidad: 20460,
};

export const sampleWithFullData: IMateriales = {
  id: 59429,
  descripcion: 'Rojo transmitter Pescado',
  cantidad: 76470,
  cantidadEnPrestamo: 43346,
};

export const sampleWithNewData: NewMateriales = {
  descripcion: 'Pollo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
