import { ITemas, NewTemas } from './temas.model';

export const sampleWithRequiredData: ITemas = {
  id: 89167,
  codigo: 'connecting Madera',
  titulo: 'functionalities SSL virtual',
  descripcion: 'Hormigon',
};

export const sampleWithPartialData: ITemas = {
  id: 31428,
  codigo: 'enterprise',
  titulo: 'acompasada Riera',
  descripcion: 'Desarrollador Cliente online',
};

export const sampleWithFullData: ITemas = {
  id: 39875,
  codigo: 'enable Islandia',
  titulo: 'Juguetería Realineado',
  descripcion: 'compuesto Borders',
};

export const sampleWithNewData: NewTemas = {
  codigo: 'Cataluña',
  titulo: 'Parafarmacia Subida Director',
  descripcion: 'technologies generating Uganda',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
