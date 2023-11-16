import dayjs from 'dayjs/esm';

import { IClientes, NewClientes } from './clientes.model';

export const sampleWithRequiredData: IClientes = {
  id: 88470,
  nombres: 'Auto Comunidad partnerships',
  apellidos: 'Práctico',
  razonSocial: 'Teclado',
  documento: 'Solar intermedia',
};

export const sampleWithPartialData: IClientes = {
  id: 38520,
  nombres: 'synergize tolerante disintermediate',
  apellidos: 'functionalities Cine',
  razonSocial: 'Electrónica',
  documento: 'productize',
  email: 'Victoria.Olivas71@yahoo.com',
  direccion: 'online',
};

export const sampleWithFullData: IClientes = {
  id: 62321,
  ruc: 'payment',
  nombres: 'Comoro Hormigon Refinado',
  apellidos: 'leverage Plástico',
  razonSocial: 'paradigma',
  documento: 'matrix Personal y',
  email: 'Margarita_Mendoza@yahoo.com',
  telefono: 'Ecuador Gerente',
  fechaNacimiento: dayjs('2023-11-15'),
  direccion: 'Cliente Open-source',
};

export const sampleWithNewData: NewClientes = {
  nombres: 'Atún',
  apellidos: 'Administrador superestructura Configurable',
  razonSocial: 'Grupo Account Trinidad',
  documento: 'eyeballs',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
