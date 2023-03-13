import { IMateriales, NewMateriales } from './materiales.model';

export const sampleWithRequiredData: IMateriales = {
  id: 51682,
  descripcion: 'Hecho Avanzado withdrawal',
  estado: 'Refinado Técnico hack',
  cantidad: 2067,
};

export const sampleWithPartialData: IMateriales = {
  id: 57189,
  descripcion: 'Amarillo Rojo transmitter',
  estado: 'dynamic',
  cantidad: 68574,
};

export const sampleWithFullData: IMateriales = {
  id: 76470,
  descripcion: 'Rústico vortals',
  estado: 'Amarillo',
  cantidad: 67334,
};

export const sampleWithNewData: NewMateriales = {
  descripcion: 'Metal',
  estado: 'Morado',
  cantidad: 990,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
