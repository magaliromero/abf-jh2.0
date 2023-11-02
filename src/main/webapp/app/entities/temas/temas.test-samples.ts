import { ITemas, NewTemas } from './temas.model';

export const sampleWithRequiredData: ITemas = {
  id: 28554,
  titulo: 'vice ordinary',
  descripcion: 'drat whenever',
};

export const sampleWithPartialData: ITemas = {
  id: 4014,
  titulo: 'uh-huh witty',
  descripcion: 'save wonderfully march',
};

export const sampleWithFullData: ITemas = {
  id: 20580,
  titulo: 'hail digital',
  descripcion: 'dupe crossly hmph',
};

export const sampleWithNewData: NewTemas = {
  titulo: 'or',
  descripcion: 'vegetation unexpectedly before',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
