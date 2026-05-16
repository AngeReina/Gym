import { Utils } from '../Utils.js';

export class PlanesView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
    this._eventsInitialized = false;
  }

  async load() {
    try {
      const data = await this.api.get('/api/planes');
      this.renderTable(data);
    } catch (e) {
      document.getElementById('tbody-planes').innerHTML =
        `<tr><td colspan="4" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }

    if (!this._eventsInitialized) {
      this._initEvents();
      this._eventsInitialized = true;
    }
  }

  renderTable(data) {
    const tb = document.getElementById('tbody-planes');
    document.getElementById('cnt-planes').textContent = data.length;

    document.querySelector('#tbody-planes').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID</th><th>Nombre</th><th>Costo</th><th>Duracion</th>';

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="4" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map(p => `
      <tr>
        <td><span class="badge badge-yellow">${Utils.or(p.idPlan ?? p.id)}</span></td>
        <td>${Utils.or(p.nombrePlan)}</td>
        <td style="color:var(--success)">${Utils.formatCOP(p.costo)}</td>
        <td>${Utils.or(p.duracionDias)} dias</td>
      </tr>`).join('');
  }

  renderMasComprados(data) {
    const tb = document.getElementById('tbody-planes');
    document.getElementById('cnt-planes').textContent = data.length;

    document.querySelector('#tbody-planes').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID</th><th>Nombre</th><th>Total Comprados</th><th></th>';

    tb.innerHTML = data.map((row, i) => `
      <tr>
        <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
        <td>${i === 0 ? '🏆 ' : ''}${row[1] ?? '—'}</td>
        <td><span class="badge badge-green">${row[2] ?? 0}</span></td>
        <td></td>
      </tr>`).join('') || `<tr><td colspan="4" class="empty-state">Sin datos</td></tr>`;
  }

  renderIngresos(data) {
    const tb = document.getElementById('tbody-planes');
    document.getElementById('cnt-planes').textContent = data.length;

    document.querySelector('#tbody-planes').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID</th><th>Nombre Plan</th><th>Ingresos Totales</th><th></th>';

    tb.innerHTML = data.map((row, i) => `
      <tr>
        <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
        <td>${i === 0 ? '💰 ' : ''}${row[1] ?? '—'}</td>
        <td style="color:var(--success)">${Utils.formatCOP(row[2])}</td>
        <td></td>
      </tr>`).join('') || `<tr><td colspan="4" class="empty-state">Sin datos</td></tr>`;
  }

  renderActivos(data) {
    const tb = document.getElementById('tbody-planes');
    document.getElementById('cnt-planes').textContent = data.length;

    document.querySelector('#tbody-planes').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID</th><th>Nombre Plan</th><th>Suscripciones Activas</th><th></th>';

    tb.innerHTML = data.map(row => `
      <tr>
        <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
        <td>${row[1] ?? '—'}</td>
        <td><span class="badge badge-green">${row[2] ?? 0}</span></td>
        <td></td>
      </tr>`).join('') || `<tr><td colspan="4" class="empty-state">Sin datos</td></tr>`;
  }

  _initEvents() {
    const filterSelect = document.getElementById('select-filter-plan');
    if (filterSelect) {
      filterSelect.onchange = async (e) => {
        const val = e.target.value;
        document.getElementById('plan-filter-dias').style.display = val === 'largos' ? 'flex' : 'none';

        if (val === 'todos')          await this.load();
        else if (val === 'precio')    await this.loadPorPrecio();
        else if (val === 'comprados') await this.loadMasComprados();
        else if (val === 'ingresos')  await this.loadIngresosPorPlan();
        else if (val === 'activos')   await this.loadPlanesActivos();
      };
    }

    const btnDias = document.getElementById('btn-filter-dias-plan');
    if (btnDias) {
      btnDias.onclick = async () => {
        const dias = document.getElementById('input-dias-plan').value;
        if (dias) await this.loadPlanesLargos(Number(dias));
      };
    }
  }

  async loadPorPrecio() {
    try {
      const data = await this.api.get('/api/planes/por-precio');
      this.renderTable(data);
    } catch (e) {
      this.toast.error('Error: ' + e.message);
    }
  }

  async loadPlanesLargos(dias) {
    try {
      const data = await this.api.get(`/api/planes/largos?dias=${dias}`);
      this.renderTable(data);
    } catch (e) {
      this.toast.error('Error: ' + e.message);
    }
  }

  async loadMasComprados() {
    try {
      const data = await this.api.get('/api/planes/mas-comprados');
      this.renderMasComprados(data);
    } catch (e) {
      this.toast.error('Error: ' + e.message);
    }
  }

  async loadIngresosPorPlan() {
    try {
      const data = await this.api.get('/api/planes/ingresos');
      this.renderIngresos(data);
    } catch (e) {
      this.toast.error('Error: ' + e.message);
    }
  }

  async loadPlanesActivos() {
    try {
      const data = await this.api.get('/api/planes/activos');
      this.renderActivos(data);
    } catch (e) {
      this.toast.error('Error: ' + e.message);
    }
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-plan');

    data.precio = parseFloat(data.precio);
    data.duracionDias = parseInt(data.duracionDias);

    try {
      await this.api.post('/api/planes', data);
      this.toast.success('Plan creado correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }
}