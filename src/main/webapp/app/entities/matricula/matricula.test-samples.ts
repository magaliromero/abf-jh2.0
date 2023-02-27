import dayjs from 'dayjs/esm';

import { EstadosPagos } from 'app/entities/enumerations/estados-pagos.model';

import { IMatricula, NewMatricula } from './matricula.model';

export const sampleWithRequiredData: IMatricula = {
  id: 55032,
  fechaInscripcion: dayjs('2023-02-27'),
  fechaInicio: dayjs('2023-02-27'),
  estado: EstadosPagos['ANULADO'],
};

export const sampleWithPartialData: IMatricula = {
  id: 17688,
  fechaInscripcion: dayjs('2023-02-27'),
  fechaInicio: dayjs('2023-02-27'),
  fechaPago: dayjs('2023-02-27'),
  estado: EstadosPagos['ANULADO'],
};

export const sampleWithFullData: IMatricula = {
  id: 18485,
  fechaInscripcion: dayjs('2023-02-27'),
  fechaInicio: dayjs('2023-02-27'),
  fechaPago: dayjs('2023-02-27'),
  estado: EstadosPagos['ANULADO'],
};

export const sampleWithNewData: NewMatricula = {
  fechaInscripcion: dayjs('2023-02-27'),
  fechaInicio: dayjs('2023-02-27'),
  estado: EstadosPagos['PENDIENTE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
