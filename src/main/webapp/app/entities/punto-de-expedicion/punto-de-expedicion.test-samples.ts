import { IPuntoDeExpedicion, NewPuntoDeExpedicion } from './punto-de-expedicion.model';

export const sampleWithRequiredData: IPuntoDeExpedicion = {
  id: 1349,
  numeroPuntoDeExpedicion: 7249,
};

export const sampleWithPartialData: IPuntoDeExpedicion = {
  id: 27741,
  numeroPuntoDeExpedicion: 14498,
};

export const sampleWithFullData: IPuntoDeExpedicion = {
  id: 28800,
  numeroPuntoDeExpedicion: 28191,
};

export const sampleWithNewData: NewPuntoDeExpedicion = {
  numeroPuntoDeExpedicion: 30163,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
