import { IPuntoDeExpedicion, NewPuntoDeExpedicion } from './punto-de-expedicion.model';

export const sampleWithRequiredData: IPuntoDeExpedicion = {
  id: 49237,
  numeroPuntoDeExpedicion: 58651,
};

export const sampleWithPartialData: IPuntoDeExpedicion = {
  id: 86551,
  numeroPuntoDeExpedicion: 52133,
};

export const sampleWithFullData: IPuntoDeExpedicion = {
  id: 64724,
  numeroPuntoDeExpedicion: 35771,
};

export const sampleWithNewData: NewPuntoDeExpedicion = {
  numeroPuntoDeExpedicion: 69515,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
