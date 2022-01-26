import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        data: { pageTitle: 'practicaDtoApp.cliente.home.title' },
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ClienteModule),
      },
      {
        path: 'venta',
        data: { pageTitle: 'practicaDtoApp.venta.home.title' },
        loadChildren: () => import('./venta/venta.module').then(m => m.VentaModule),
      },
      {
        path: 'empleado',
        data: { pageTitle: 'practicaDtoApp.empleado.home.title' },
        loadChildren: () => import('./empleado/empleado.module').then(m => m.EmpleadoModule),
      },
      {
        path: 'coche',
        data: { pageTitle: 'practicaDtoApp.coche.home.title' },
        loadChildren: () => import('./coche/coche.module').then(m => m.CocheModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
