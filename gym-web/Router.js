export class Router {
  constructor(viewLoader) {
    this.viewLoader = viewLoader;

    this.titles = {
      dashboard:     'Dashboard',
      clientes:      'Clientes',
      instructores:  'Instructores',
      clases:        'Clases',
      sesiones:      'Sesiones',
      asistencias:   'Asistencias',
      planes:        'Planes',
      suscripciones: 'Suscripciones',
      facturas:      'Facturas',
      pagos:         'Pagos',
      reportes:      'Reportes',
    };

    this._bindNavItems();
  }

  navigate(view) {
    document.querySelectorAll('.view')
      .forEach(v => v.classList.remove('active'));
    document.querySelectorAll('.nav-item')
      .forEach(n => n.classList.remove('active'));

    const viewEl = document.getElementById('view-' + view);
    const navEl  = document.querySelector(`[data-view="${view}"]`);

    if (viewEl) viewEl.classList.add('active');
    if (navEl)  navEl.classList.add('active');

    document.getElementById('page-title').textContent =
      this.titles[view] || view;

    this.viewLoader(view);
  }

  _bindNavItems() {
    document.querySelectorAll('.nav-item').forEach(item => {
      item.addEventListener('click', () => {
        this.navigate(item.dataset.view);
      });
    });
  }
}