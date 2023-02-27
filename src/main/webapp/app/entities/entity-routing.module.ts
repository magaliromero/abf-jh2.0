import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tipos-documentos',
        data: { pageTitle: 'abfApp.tiposDocumentos.home.title' },
        loadChildren: () => import('./tipos-documentos/tipos-documentos.module').then(m => m.TiposDocumentosModule),
      },
      {
        path: 'alumnos',
        data: { pageTitle: 'abfApp.alumnos.home.title' },
        loadChildren: () => import('./alumnos/alumnos.module').then(m => m.AlumnosModule),
      },
      {
        path: 'funcionarios',
        data: { pageTitle: 'abfApp.funcionarios.home.title' },
        loadChildren: () => import('./funcionarios/funcionarios.module').then(m => m.FuncionariosModule),
      },
      {
        path: 'temas',
        data: { pageTitle: 'abfApp.temas.home.title' },
        loadChildren: () => import('./temas/temas.module').then(m => m.TemasModule),
      },
      {
        path: 'malla-curricular',
        data: { pageTitle: 'abfApp.mallaCurricular.home.title' },
        loadChildren: () => import('./malla-curricular/malla-curricular.module').then(m => m.MallaCurricularModule),
      },
      {
        path: 'matricula',
        data: { pageTitle: 'abfApp.matricula.home.title' },
        loadChildren: () => import('./matricula/matricula.module').then(m => m.MatriculaModule),
      },
      {
        path: 'registro-clases',
        data: { pageTitle: 'abfApp.registroClases.home.title' },
        loadChildren: () => import('./registro-clases/registro-clases.module').then(m => m.RegistroClasesModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
