import dayjs from 'dayjs/esm';
import { IAlumnos } from 'app/entities/alumnos/alumnos.model';
import { IFuncionarios } from 'app/entities/funcionarios/funcionarios.model';

export interface IPagos {
  id: number;
  montoPago?: number | null;
  montoInicial?: number | null;
  saldo?: number | null;
  fechaRegistro?: dayjs.Dayjs | null;
  fechaPago?: dayjs.Dayjs | null;
  tipoPago?: string | null;
  descripcion?: string | null;
  idUsuarioRegistro?: number | null;
  alumnos?: Pick<IAlumnos, 'id' | 'nombreCompleto'> | null;
  funcionarios?: Pick<IFuncionarios, 'id' | 'nombreCompleto'> | null;
}

export type NewPagos = Omit<IPagos, 'id'> & { id: null };
