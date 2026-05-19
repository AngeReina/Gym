import { ApiService }         from './ApiService.js';
import { ToastService }       from './Toast.js';
import { Router }             from './Router.js';

import { DashboardView }      from './views/DashboardsView.js';
import { ClientesView }       from './views/ClienteView.js';
import { InstructoresView }   from './views/InstructoresView.js';
import { ClasesView }         from './views/ClasesView.js';
import { SesionesView }       from './views/SesionView.js';
import { AsistenciasView }    from './views/AsistenciaView.js';
import { PlanesView }         from './views/PlanesView.js';
import { SuscripcionesView }  from './views/SuscripcionesView.js';
import { FacturasView }       from './views/FacturasView.js';
import { PagosView }          from './views/PagosView.js';
import { ReportesView }       from './views/ReportesView.js';

class App {

  constructor() {

    this.api   = new ApiService('http://localhost:8080');
    this.toast = new ToastService('toast-container');

    this.views = {

      dashboard:      new DashboardView(this.api),

      clientes:       new ClientesView(
        this.api,
        this.toast
      ),

      instructores:   new InstructoresView(
        this.api,
        this.toast
      ),

      clases:         new ClasesView(
        this.api,
        this.toast
      ),

      sesiones:       new SesionesView(
        this.api,
        this.toast
      ),

      asistencias:    new AsistenciasView(
        this.api,
        this.toast
      ),

      planes:         new PlanesView(
        this.api,
        this.toast
      ),

      suscripciones:  new SuscripcionesView(
        this.api,
        this.toast
      ),

      facturas:       new FacturasView(
        this.api,
        this.toast
      ),

      pagos:          new PagosView(
        this.api,
        this.toast
      ),

      reportes:       new ReportesView(
        this.api,
        this.toast
      )
    };

     this.router = new Router(
      (view) => this._loadView(view)
    );

     this._bindForms();
    this._bindDashboardButtons();
    this._bindPagosAddButton();
    this._init();
  }

   async _loadView(viewName) {

    const view = this.views[viewName];

    if (!view) return;

    try {

      if (typeof view.load === 'function') {
        await view.load();
      }

      if (
        viewName === 'clientes' &&
        typeof view.initEvents === 'function'
      ) {
        view.initEvents();
      }

    } catch (e) {

      console.error(e);

      this.toast.error(
        'Error cargando vista: ' + e.message
      );
    }
  }

  _bindForms() {

    const forms = [

      { formId: 'form-cliente',     view: 'clientes' },
      { formId: 'form-instructor',  view: 'instructores' },
      { formId: 'form-clase',       view: 'clases' },
      { formId: 'form-sesion',      view: 'sesiones' },
      { formId: 'form-asistencia',  view: 'asistencias' },
      { formId: 'form-plan',        view: 'planes' },
      { formId: 'form-suscripcion', view: 'suscripciones' },
      { formId: 'form-factura',     view: 'facturas' },
      { formId: 'form-pago',        view: 'pagos' }

    ];

    forms.forEach(({ formId, view }) => {

      const form = document.getElementById(formId);

      if (!form) return;

      form.addEventListener('submit', async (e) => {

        e.preventDefault();

        try {

          if (
            this.views[view] &&
            typeof this.views[view].submit === 'function'
          ) {
            await this.views[view].submit(e);
          }

        } catch (err) {

          console.error(err);

          this.toast.error(
            err.message || 'Error procesando formulario'
          );
        }
      });
    });
  }

  _bindDashboardButtons() {

    document
      .querySelectorAll('[data-navigate]')
      .forEach(btn => {

        btn.addEventListener('click', () => {

          const view = btn.dataset.navigate;

          if (view) {
            this.router.navigate(view);
          }
        });
      });
  }

  _bindPagosAddButton() {

    const btn = document.getElementById(
      'btn-add-metodo'
    );

    if (!btn) return;

    btn.addEventListener('click', () => {

      this.views.pagos.addMetodo();
    });
  }

  async _init() {

    try {

      await this.views.dashboard.load();

      const metodosContainer =
        document.getElementById('metodos-container');

      if (
        metodosContainer &&
        metodosContainer.children.length === 0
      ) {
        this.views.pagos.addMetodo();
      }

    } catch (e) {

      console.error(e);

      this.toast.error(
        'Error inicializando aplicación'
      );
    }
  }
}
document.addEventListener(
  'DOMContentLoaded',
  () => new App()
);
document.addEventListener('click', (e) => {
  // Cierra todos si se hace click afuera
  document.querySelectorAll('.custom-select-menu.open').forEach(m => {
    if (!m.closest('.custom-select').contains(e.target)) {
      m.classList.remove('open');
    }
  });

  const btn = e.target.closest('.custom-select-btn');
  if (btn) {
    const menu = btn.nextElementSibling;
    menu.classList.toggle('open');
  }

  const opt = e.target.closest('.custom-select-option');
  if (opt) {
    const menu   = opt.closest('.custom-select-menu');
    const cs     = opt.closest('.custom-select');
    const button = cs.querySelector('.custom-select-btn');

    menu.querySelectorAll('.custom-select-option').forEach(o => o.classList.remove('selected'));
    opt.classList.add('selected');

    const textNode = [...button.childNodes].find(n => n.nodeType === 3 && n.textContent.trim() !== '');
    if (textNode) textNode.textContent = opt.textContent;

    menu.classList.remove('open');

    cs.dispatchEvent(new CustomEvent('cs-change', {
      detail: { value: opt.dataset.value },
      bubbles: true
    }));
  }
});