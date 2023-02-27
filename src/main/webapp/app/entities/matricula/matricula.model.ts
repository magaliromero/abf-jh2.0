import dayjs from 'dayjs/esm';
import { IAlumnos } from 'app/entities/alumnos/alumnos.model';
import { EstadosPagos } from 'app/entities/enumerations/estados-pagos.model';

export interface IMatricula {
  id: number;
  fechaInscripcion?: dayjs.Dayjs | null;
  fechaInicio?: dayjs.Dayjs | null;
  fechaPago?: dayjs.Dayjs | null;
  estado?: EstadosPagos | null;
  alumnos?: Pick<IAlumnos, 'id' | 'nombreCompleto'> | null;
}

export type NewMatricula = Omit<IMatricula, 'id'> & { id: null };
