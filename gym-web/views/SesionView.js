import { Utils } from '../Utils.js';

export class SesionesView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
    this._eventsInitialized = false;
  }

  async load() {
    try {
      const [clases, instructores] = await Promise.all([
        this.api.get('/api/clases'),
        this.api.get('/api/instructores'),
      ]);

      Utils.populateSelect(
        'sel-clase-sesion', clases,
        c => c.idClase ?? c.id,
        c => c.nombreClase ?? `Clase ${c.id}`
      );

      Utils.populateSelect(
        'sel-instructor-sesion', instructores,
        i => i.idInstructor ?? i.id,
        i => `${i.primerNombre ?? ''} ${i.primerApellido ?? ''}`
      );

      Utils.populateSelect(
        'sel-filter-instructor-sesion', instructores,
        i => i.idInstructor ?? i.id,
        i => `${i.primerNombre ?? ''} ${i.primerApellido ?? ''}`
      );

      const data = await this.api.get('/api/sesiones');
      this.renderTable(data);

    } catch (e) {
      document.getElementById('tbody-sesiones').innerHTML =
        `<tr><td colspan="6" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }

    if (!this._eventsInitialized) {
      this._initEvents();
      this._eventsInitialized = true;
    }
  }

  renderTable(data) {
    const tb = document.getElementById('tbody-sesiones');
    document.getElementById('cnt-sesiones').textContent = data.length;

    if (!data.length) {
        tb.innerHTML = `<tr><td colspan="6" class="empty-state">Sin registros</td></tr>`;
        return;
    }

    document.querySelector('#tbody-sesiones')
        .closest('table')
        .querySelector('thead tr').innerHTML =
        '<th>ID</th><th>Clase</th><th>Instructor</th><th>Fecha</th><th>Hora</th><th>Cupos</th>';

    tb.innerHTML = data.map(s => `
        <tr>
            <td>
                <span class="badge badge-yellow">
                    ${Utils.or(s.idSesion)}
                </span>
            </td>

            <td>${Utils.or(s.clase)}</td>

            <td>${Utils.or(s.instructor)}</td>

            <td>${Utils.or(s.fecha)}</td>

            <td style="font-size:12px; color:var(--muted)">
                ${Utils.or(s.horaInicio)} - ${Utils.or(s.horaFin)}
            </td>

            <td>
                ${Utils.or(s.cuposDisponibles)} /
                ${Utils.or(s.cupoMax)}
            </td>
        </tr>
    `).join('');
    }
  renderTableFromArray(data) {
    const tb = document.getElementById('tbody-sesiones');
    document.getElementById('cnt-sesiones').textContent = data.length;

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="6" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map(row => {
      const cols = Array.isArray(row) ? row : Object.values(row);
      return `<tr>${cols.map(v => `<td>${Utils.or(v)}</td>`).join('')}</tr>`;
    }).join('');
  }

  renderMasAsistidas(data) {
    const tb = document.getElementById('tbody-sesiones');
    document.getElementById('cnt-sesiones').textContent = data.length;

    document.querySelector('#tbody-sesiones').closest('table').querySelector('thead tr').innerHTML =
      '<th>ID Sesion</th><th>Total Asistencias</th><th></th><th></th><th></th><th></th>';

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="6" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map((row, i) => `
      <tr>
        <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
        <td><span class="badge badge-green">${i === 0 ? '🏆 ' : ''}${row[1] ?? 0} asistencias</span></td>
        <td colspan="4"></td>
      </tr>`).join('');
  }

  _initEvents() {
    const filterSelect = document.getElementById('select-filter-sesion');
    if (filterSelect) {
      filterSelect.onchange = async (e) => {
        const val = e.target.value;
        document.getElementById('sesion-filter-fecha').style.display    = val === 'por-fecha'      ? 'flex' : 'none';
        document.getElementById('sesion-filter-instructor').style.display = val === 'por-instructor' ? 'flex' : 'none';

        if (val === 'todos') {
          await this._reloadSesiones();
        } else if (val === 'con-cupos') {
          await this.loadConCupos();
        } else if (val === 'mas-asistidas') {
          await this.loadMasAsistidas();
        }
      };
    }

    const btnFecha = document.getElementById('btn-filter-fecha-sesion');
    if (btnFecha) {
      btnFecha.onclick = async () => {
        const fecha = document.getElementById('input-fecha-sesion').value;
        if (fecha) await this.loadPorFecha(fecha);
      };
    }

    const btnInstructor = document.getElementById('btn-filter-instructor-sesion');
    if (btnInstructor) {
      btnInstructor.onclick = async () => {
        const id = document.getElementById('sel-filter-instructor-sesion').value;
        if (id) await this.loadPorInstructor(id);
      };
    }
  }

  async _reloadSesiones() {
    try {
      const data = await this.api.get('/api/sesiones');
      this.renderTable(data);
    } catch (e) {
      this.toast.error('Error al cargar sesiones: ' + e.message);
    }
  }

  async loadPorFecha(fecha) {
    try {
      const data = await this.api.get(`/api/sesiones/por-fecha?fecha=${fecha}`);
      const tb = document.getElementById('tbody-sesiones');
      document.getElementById('cnt-sesiones').textContent = data.length;
      document.querySelector('#tbody-sesiones').closest('table').querySelector('thead tr').innerHTML =
        '<th>ID</th><th>Fecha</th><th>Clase</th><th>Instructor</th><th></th><th></th>';
      tb.innerHTML = data.map(row => `
        <tr>
          <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
          <td>${row[1] ?? '—'}</td>
          <td>${row[2] ?? '—'}</td>
          <td>${row[3] ?? ''} ${row[4] ?? ''}</td>
          <td colspan="2"></td>
        </tr>`).join('') || `<tr><td colspan="6" class="empty-state">Sin sesiones en esa fecha</td></tr>`;
    } catch (e) {
      this.toast.error('Error al filtrar por fecha: ' + e.message);
    }
  }

  async loadPorInstructor(idInstructor) {
    try {
      const data = await this.api.get(`/api/sesiones/instructor/${idInstructor}`);
      const tb = document.getElementById('tbody-sesiones');
      document.getElementById('cnt-sesiones').textContent = data.length;
      document.querySelector('#tbody-sesiones').closest('table').querySelector('thead tr').innerHTML =
        '<th>ID</th><th>Fecha</th><th>Clase</th><th>Cupos Disponibles</th><th></th><th></th>';
      tb.innerHTML = data.map(row => `
        <tr>
          <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
          <td>${row[1] ?? '—'}</td>
          <td>${row[2] ?? '—'}</td>
          <td>${row[3] ?? '—'}</td>
          <td colspan="2"></td>
        </tr>`).join('') || `<tr><td colspan="6" class="empty-state">Sin sesiones para este instructor</td></tr>`;
    } catch (e) {
      this.toast.error('Error al filtrar por instructor: ' + e.message);
    }
  }

  async loadConCupos() {
    try {
      const data = await this.api.get('/api/sesiones/con-cupos');
      const tb = document.getElementById('tbody-sesiones');
      document.getElementById('cnt-sesiones').textContent = data.length;
      document.querySelector('#tbody-sesiones').closest('table').querySelector('thead tr').innerHTML =
        '<th>ID</th><th>Fecha</th><th>Clase</th><th>Cupos Disponibles</th><th></th><th></th>';
      tb.innerHTML = data.map(row => `
        <tr>
          <td><span class="badge badge-yellow">${row[0] ?? '—'}</span></td>
          <td>${row[1] ?? '—'}</td>
          <td>${row[2] ?? '—'}</td>
          <td><span class="badge badge-green">${row[3] ?? 0}</span></td>
          <td colspan="2"></td>
        </tr>`).join('') || `<tr><td colspan="6" class="empty-state">Sin sesiones con cupos</td></tr>`;
    } catch (e) {
      this.toast.error('Error al filtrar con cupos: ' + e.message);
    }
  }

  async loadMasAsistidas() {
    try {
      const data = await this.api.get('/api/sesiones/mas-asistidas');
      this.renderMasAsistidas(data);
    } catch (e) {
      this.toast.error('Error al cargar sesiones más asistidas: ' + e.message);
    }
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-sesion');

    data.idClase      = Number(data.idClase);
    data.idInstructor = Number(data.idInstructor);
    data.cupoMax      = Number(data.cupoMax);

    try {
      await this.api.post('/api/sesiones', data);
      this.toast.success('Sesion creada correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }
}