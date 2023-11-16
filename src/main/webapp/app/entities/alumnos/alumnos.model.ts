import dayjs from 'dayjs/esm';
import { ITiposDocumentos } from 'app/entities/tipos-documentos/tipos-documentos.model';
import { EstadosPersona } from 'app/entities/enumerations/estados-persona.model';

export interface IAlumnos {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  nombreCompleto?: string | null;
  email?: string | null;
  telefono?: string | null;
  fechaNacimiento?: dayjs.Dayjs | null;
  documento?: string | null;
  estado?: EstadosPersona | null;
  elo?: number | null;
  fideId?: number | null;
  tipoDocumentos?: Pick<ITiposDocumentos, 'id' | 'descripcion'> | null;
}

export type NewAlumnos = Omit<IAlumnos, 'id'> & { id: null };
