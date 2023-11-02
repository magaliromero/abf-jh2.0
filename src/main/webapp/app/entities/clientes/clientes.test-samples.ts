import dayjs from 'dayjs/esm';

import { IClientes, NewClientes } from './clientes.model';

export const sampleWithRequiredData: IClientes = {
  id: 11756,
  nombres: 'arm reshuffle heavily',
  apellidos: 'yum without inasmuch',
  razonSocial: 'times',
  documento: 'instrumentalist',
};

export const sampleWithPartialData: IClientes = {
  id: 17936,
  nombres: 'consequently offensively',
  apellidos: 'separate mean evil',
  razonSocial: 'meh inventory wide-eyed',
  documento: 'and worth',
  telefono: 'even tame',
  direccion: 'unaccountably',
};

export const sampleWithFullData: IClientes = {
  id: 21046,
  ruc: 'fret tomorrow',
  nombres: 'given fully',
  apellidos: 'hmph',
  razonSocial: 'needily full swath',
  documento: 'how finally',
  email: 'Sierra78@hotmail.com',
  telefono: 'decree nicely shaw',
  fechaNacimiento: dayjs('2023-05-31'),
  direccion: 'lively blah mayonnaise',
};

export const sampleWithNewData: NewClientes = {
  nombres: 'phew anenst',
  apellidos: 'oof towards midst',
  razonSocial: 'stealthily',
  documento: 'frenetically fasten',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
