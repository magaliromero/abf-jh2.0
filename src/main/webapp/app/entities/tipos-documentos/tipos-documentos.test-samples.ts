import { ITiposDocumentos, NewTiposDocumentos } from './tipos-documentos.model';

export const sampleWithRequiredData: ITiposDocumentos = {
  id: 19795,
  codigo: 'bewitched gee though',
  descripcion: 'on exorcize round',
};

export const sampleWithPartialData: ITiposDocumentos = {
  id: 19219,
  codigo: 'musculature apropos',
  descripcion: 'adventurously acclaimed',
};

export const sampleWithFullData: ITiposDocumentos = {
  id: 25689,
  codigo: 'anchored',
  descripcion: 'quieten meteor along',
};

export const sampleWithNewData: NewTiposDocumentos = {
  codigo: 'outstay mortally wrongly',
  descripcion: 'unnaturally reporting hairy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
