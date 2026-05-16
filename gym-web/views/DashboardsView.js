export class DashboardView {
  constructor(api) {
    this.api = api;
  }

  async load() {
    const endpoints = [
      { path: '/api/clientes',      elId: 'stat-clientes' },
      { path: '/api/instructores',  elId: 'stat-instructores' },
      { path: '/api/sesiones',      elId: 'stat-sesiones' },
      { path: '/api/facturas',      elId: 'stat-facturas' },
      { path: '/api/planes',        elId: 'stat-planes' },
      { path: '/api/suscripciones', elId: 'stat-suscrip' },
    ];

    for (const { path, elId } of endpoints) {
      this.api.get(path)
        .then(data => {
          document.getElementById(elId).textContent =
            Array.isArray(data) ? data.length : '?';
        })
        .catch(() => {
          document.getElementById(elId).textContent = 'N/A';
        });
    }
  }
}
