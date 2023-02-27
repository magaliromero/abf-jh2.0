import { ITiposDocumentos, NewTiposDocumentos } from './tipos-documentos.model';

export const sampleWithRequiredData: ITiposDocumentos = {
  id: 68091,
  codigo: 'connect deposit Pequeño',
  descripcion: 'Peso Avon Loan',
};

export const sampleWithPartialData: ITiposDocumentos = {
  id: 41468,
  codigo: 'Asociado conocimiento applications',
  descripcion: 'Urbanización dot-com',
};

export const sampleWithFullData: ITiposDocumentos = {
  id: 36840,
  codigo: 'Analista Lituania',
  descripcion: 'CSS SCSI',
};

export const sampleWithNewData: NewTiposDocumentos = {
  codigo: 'Consultor Música Ladrillo',
  descripcion: 'RSS index',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
