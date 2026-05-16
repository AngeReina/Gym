import { Utils } from '../Utils.js';

export class ClientesView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
    this._allData = [];
  }

  async load() {
    try {
      const data = await this.api.get('/api/clientes');
      this._allData = data;
      this.renderTable(data);
    } catch (e) {
      document.getElementById('tbody-clientes').innerHTML =
        `<tr><td colspan="5" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  renderTable(data) {
    const tb = document.getElementById('tbody-clientes');
    document.getElementById('cnt-clientes').textContent = data.length;

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="5" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map(c => `
      <tr>
        <td><span class="badge badge-yellow">${Utils.or(c.idCliente ?? c.id)}</span></td>
        <td>${Utils.or(c.primerNombre, '')} ${Utils.or(c.segundoNombre, '')} ${Utils.or(c.primerApellido, '')} ${Utils.or(c.segundoApellido, '')}</td>
        <td>${Utils.or(c.tipoDocumento, '')} ${Utils.or(c.numeroDocumento, '')}</td>
        <td>${Utils.or(c.telefono)}</td>
        <td>${Utils.or(c.email)}</td>
      </tr>`).join('');
  }

  renderRankingTable(data) {
    const tb = document.getElementById('tbody-clientes');
    document.getElementById('cnt-clientes').textContent = data.length;

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="5" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map((row, i) => `
      <tr>
        <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
        <td>${i === 0 ? '🥇 ' : i === 1 ? '🥈 ' : i === 2 ? '🥉 ' : ''}Cliente #${row[0] ?? '—'}</td>
        <td colspan="2" style="color:var(--muted); font-size:12px">Asistencias registradas</td>
        <td><span class="badge badge-green">${row[1] ?? 0}</span></td>
      </tr>`).join('');
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-cliente');

    try {
      await this.api.post('/api/clientes', data);
      this.toast.success('Cliente registrado correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }

  initEvents() {
    const searchInput = document.getElementById('input-search-cliente');
    if (searchInput) {
        searchInput.oninput = (e) => this.filterTable(e.target.value);
    }
    
    const csFilterCliente = document.getElementById('cs-filter-cliente');
    if (csFilterCliente) {
        csFilterCliente.addEventListener('cs-change', async (e) => {
        const val = e.detail.value;
        if (val === 'ranking') {
            await this.loadRankingAsistencia();
        } else {
            await this.load();
        }
        });
    }
    }

  filterTable(query) {
    const rows = document.querySelectorAll('#tbody-clientes tr');
    const q = query.toLowerCase();
    rows.forEach(row => {
      row.style.display = row.innerText.toLowerCase().includes(q) ? '' : 'none';
    });
  }

  async loadRankingAsistencia() {
    try {
      const data = await this.api.get('/api/clientes/ranking');
      this.renderRankingTable(data);
    } catch (e) {
      this.toast.error('Error al cargar ranking de asistencia: ' + e.message);
    }
  }

}