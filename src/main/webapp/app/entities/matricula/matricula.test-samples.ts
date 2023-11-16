import dayjs from 'dayjs/esm';

import { EstadosPagos } from 'app/entities/enumerations/estados-pagos.model';

import { IMatricula, NewMatricula } from './matricula.model';

export const sampleWithRequiredData: IMatricula = {
  id: 55032,
  concepto: 'Ouguiya Teclado AI',
  monto: 45850,
  fechaInscripcion: dayjs('2023-11-15'),
  fechaInicio: dayjs('2023-11-15'),
  estado: EstadosPagos['PENDIENTE'],
};

export const sampleWithPartialData: IMatricula = {
  id: 73560,
  concepto: 'Salchichas generating Travesía',
  monto: 95292,
  fechaInscripcion: dayjs('2023-11-16'),
  fechaInicio: dayjs('2023-11-15'),
  fechaPago: dayjs('2023-11-15'),
  estado: EstadosPagos['ANULADO'],
};

export const sampleWithFullData: IMatricula = {
  id: 99950,
  concepto: 'CFP world-class',
  monto: 65690,
  fechaInscripcion: dayjs('2023-11-15'),
  fechaInicio: dayjs('2023-11-15'),
  fechaPago: dayjs('2023-11-15'),
  estado: EstadosPagos['ANULADO'],
};

export const sampleWithNewData: NewMatricula = {
  concepto: 'Videojuegos',
  monto: 85587,
  fechaInscripcion: dayjs('2023-11-15'),
  fechaInicio: dayjs('2023-11-15'),
  estado: EstadosPagos['PENDIENTE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
