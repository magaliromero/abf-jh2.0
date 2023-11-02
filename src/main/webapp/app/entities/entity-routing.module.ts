import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'temas',
        data: { pageTitle: 'abfApp.temas.home.title' },
        loadChildren: () => import('./temas/temas.routes'),
      },
      {
        path: 'registro-clases',
        data: { pageTitle: 'abfApp.registroClases.home.title' },
        loadChildren: () => import('./registro-clases/registro-clases.routes'),
      },
      {
        path: 'cursos',
        data: { pageTitle: 'abfApp.cursos.home.title' },
        loadChildren: () => import('./cursos/cursos.routes'),
      },
      {
        path: 'inscripciones',
        data: { pageTitle: 'abfApp.inscripciones.home.title' },
        loadChildren: () => import('./inscripciones/inscripciones.routes'),
      },
      {
        path: 'tipos-documentos',
        data: { pageTitle: 'abfApp.tiposDocumentos.home.title' },
        loadChildren: () => import('./tipos-documentos/tipos-documentos.routes'),
      },
      {
        path: 'alumnos',
        data: { pageTitle: 'abfApp.alumnos.home.title' },
        loadChildren: () => import('./alumnos/alumnos.routes'),
      },
      {
        path: 'funcionarios',
        data: { pageTitle: 'abfApp.funcionarios.home.title' },
        loadChildren: () => import('./funcionarios/funcionarios.routes'),
      },
      {
        path: 'clientes',
        data: { pageTitle: 'abfApp.clientes.home.title' },
        loadChildren: () => import('./clientes/clientes.routes'),
      },
      {
        path: 'facturas',
        data: { pageTitle: 'abfApp.facturas.home.title' },
        loadChildren: () => import('./facturas/facturas.routes'),
      },
      {
        path: 'factura-detalle',
        data: { pageTitle: 'abfApp.facturaDetalle.home.title' },
        loadChildren: () => import('./factura-detalle/factura-detalle.routes'),
      },
      {
        path: 'pagos',
        data: { pageTitle: 'abfApp.pagos.home.title' },
        loadChildren: () => import('./pagos/pagos.routes'),
      },
      {
        path: 'productos',
        data: { pageTitle: 'abfApp.productos.home.title' },
        loadChildren: () => import('./productos/productos.routes'),
      },
      {
        path: 'materiales',
        data: { pageTitle: 'abfApp.materiales.home.title' },
        loadChildren: () => import('./materiales/materiales.routes'),
      },
      {
        path: 'prestamos',
        data: { pageTitle: 'abfApp.prestamos.home.title' },
        loadChildren: () => import('./prestamos/prestamos.routes'),
      },
      {
        path: 'matricula',
        data: { pageTitle: 'abfApp.matricula.home.title' },
        loadChildren: () => import('./matricula/matricula.routes'),
      },
      {
        path: 'evaluaciones',
        data: { pageTitle: 'abfApp.evaluaciones.home.title' },
        loadChildren: () => import('./evaluaciones/evaluaciones.routes'),
      },
      {
        path: 'evaluaciones-detalle',
        data: { pageTitle: 'abfApp.evaluacionesDetalle.home.title' },
        loadChildren: () => import('./evaluaciones-detalle/evaluaciones-detalle.routes'),
      },
      {
        path: 'timbrados',
        data: { pageTitle: 'abfApp.timbrados.home.title' },
        loadChildren: () => import('./timbrados/timbrados.routes'),
      },
      {
        path: 'sucursales',
        data: { pageTitle: 'abfApp.sucursales.home.title' },
        loadChildren: () => import('./sucursales/sucursales.routes'),
      },
      {
        path: 'punto-de-expedicion',
        data: { pageTitle: 'abfApp.puntoDeExpedicion.home.title' },
        loadChildren: () => import('./punto-de-expedicion/punto-de-expedicion.routes'),
      },
      {
        path: 'nota-credito',
        data: { pageTitle: 'abfApp.notaCredito.home.title' },
        loadChildren: () => import('./nota-credito/nota-credito.routes'),
      },
      {
        path: 'nota-credito-detalle',
        data: { pageTitle: 'abfApp.notaCreditoDetalle.home.title' },
        loadChildren: () => import('./nota-credito-detalle/nota-credito-detalle.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
