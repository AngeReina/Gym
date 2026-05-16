import { Utils } from '../Utils.js';

export class InstructoresView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
    this._allData = [];
    this._eventsInitialized = false;
  }

  async load() {
    try {
      const data = await this.api.get('/api/instructores');
      this._allData = data;
      this.renderTable(data);
    } catch (e) {
      document.getElementById('tbody-instructores').innerHTML =
        `<tr><td colspan="5" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }

    if (!this._eventsInitialized) {
      this._initEvents();
      this._eventsInitialized = true;
    }
  }

  renderTable(data) {
    const tb = document.getElementById('tbody-instructores');
    document.getElementById('cnt-instructores').textContent = data.length;

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="5" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    document.querySelector('#tbody-instructores').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID</th><th>Nombre</th><th>Apellido</th><th>Especialidad</th><th>Telefono</th>';

    tb.innerHTML = data.map(i => `
      <tr>
        <td><span class="badge badge-yellow">${Utils.or(i.idInstructor ?? i.id)}</span></td>
        <td>${Utils.or(i.primerNombre, '')} ${Utils.or(i.segundoNombre, '')}</td>
        <td>${Utils.or(i.primerApellido, '')} ${Utils.or(i.segundoApellido, '')}</td>
        <td><span class="badge badge-orange">${Utils.or(i.especialidad)}</span></td>
        <td>${Utils.or(i.telefono)}</td>
      </tr>`).join('');
  }

  renderRankingTable(data) {
    const tb = document.getElementById('tbody-instructores');
    document.getElementById('cnt-instructores').textContent = data.length;

    document.querySelector('#tbody-instructores').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID</th><th>Nombre</th><th>Total Sesiones</th><th></th><th></th>';

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="5" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map((row, i) => `
      <tr>
        <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
        <td>${i === 0 ? '🥇 ' : i === 1 ? '🥈 ' : i === 2 ? '🥉 ' : ''}${row[1] ?? '—'}</td>
        <td><span class="badge badge-green">${row[2] ?? 0} sesiones</span></td>
        <td></td><td></td>
      </tr>`).join('');
  }

  _initEvents() {
    const filterSelect = document.getElementById('select-filter-instructor');
    if (filterSelect) {
      filterSelect.onchange = async (e) => {
        const val = e.target.value;
        if (val === 'todos') {
          await this.load();
        } else if (val === 'con-clases') {
          await this.loadConClases();
        } else if (val === 'ranking') {
          await this.loadRankingSesiones();
        }
      };
    }

    const searchInput = document.getElementById('input-search-instructor');
    if (searchInput) {
      searchInput.oninput = (e) => {
        const q = e.target.value.trim();
        if (q.length >= 2) {
          this.buscarPorNombre(q);
        } else if (q.length === 0) {
          this.renderTable(this._allData);
        }
      };
    }

    const especialidadInput = document.getElementById('input-especialidad-instructor');
    const btnEspecialidad   = document.getElementById('btn-filter-especialidad');
    if (btnEspecialidad && especialidadInput) {
      btnEspecialidad.onclick = async () => {
        const esp = especialidadInput.value.trim();
        if (esp) await this.loadPorEspecialidad(esp);
        else await this.load();
      };
    }
  }

  async buscarPorNombre(nombre) {
    try {
      const data = await this.api.get(`/api/instructores/buscar?nombre=${encodeURIComponent(nombre)}`);
      this.renderTable(data);
    } catch (e) {
      this.toast.error('Error al buscar: ' + e.message);
    }
  }

  async loadPorEspecialidad(especialidad) {
    try {
      const data = await this.api.get(`/api/instructores/especialidad/${encodeURIComponent(especialidad)}`);
      this.renderTable(data);
    } catch (e) {
      this.toast.error('Error al filtrar por especialidad: ' + e.message);
    }
  }

  async loadConClases() {
    try {
      const data = await this.api.get('/api/instructores/con-clases');
      this.renderTable(data);
    } catch (e) {
      this.toast.error('Error al cargar instructores con clases: ' + e.message);
    }
  }

  async loadRankingSesiones() {
    try {
      const data = await this.api.get('/api/instructores/ranking-sesiones');
      this.renderRankingTable(data);
    } catch (e) {
      this.toast.error('Error al cargar ranking: ' + e.message);
    }
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-instructor');

    try {
      await this.api.post('/api/instructores', data);
      this.toast.success('Instructor registrado correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }
}