import { Utils } from '../Utils.js';

export class AsistenciasView {
  constructor(api, toast) {
    this.api = api;
    this.toast = toast;
    this._sesionListenerAdded = false; 
    this._resetListenerAdded = false;
  }

  renderTable(data) {
    const tb = document.getElementById('tbody-asistencias');
    document.getElementById('cnt-asistencias').textContent = data.length;

    if (!data.length) {
      tb.innerHTML = `<tr><td colspan="5" class="empty-state">Sin registros</td></tr>`;
      return;
    }

    tb.innerHTML = data.map(a => `
      <tr>
        <td><span class="badge badge-yellow">${Utils.or(a.idAsistencia)}</span></td>
        <td>${Utils.or(a.clienteNombre)}</td>
        <td>${Utils.or(a.idSesion)}</td>
        <td>${Utils.or(a.fechaRegistro)}</td>
        <td><span class="badge badge-green">${Utils.or(a.estadoAsistencia, 'PRESENTE')}</span></td>
      </tr>`).join('');
  }

  async load() {
    try {
      const [clientes, sesiones] = await Promise.all([
        this.api.get('/api/clientes'),
        this.api.get('/api/sesiones'),
      ]);

      Utils.populateSelect('sel-cliente-asist', clientes, c => c.idCliente ?? c.id,
        c => `${c.primerNombre ?? ''} ${c.primerApellido ?? ''}`);

      Utils.populateSelect('sel-sesion-asist', sesiones, s => s.idSesion ?? s.id,
        s => `Sesión ${s.idSesion ?? s.id} - ${s.clase ?? ''}`);

        if (!this._sesionListenerAdded) {
        document.getElementById('sel-sesion-asist').addEventListener('change', async (e) => {
          const id = e.target.value;
          try {
            if (id) {
              const filtrados = await this.api.get(`/api/asistencias/sesion/${id}`);
              this.renderTable(filtrados);
            } else {
              const todas = await this.api.get('/api/asistencias');
              this.renderTable(todas);
            }
          } catch (err) {
            this.toast.error('Error al filtrar asistencias: ' + err.message);
          }
        });
        this._sesionListenerAdded = true;
        const btnReset = document.getElementById('btn-reset-asistencias');

        if (btnReset && !this._resetListenerAdded) {
            btnReset.addEventListener('click', async () => {
            document.getElementById('sel-sesion-asist').value = '';

        const todas = await this.api.get('/api/asistencias');
        this.renderTable(todas);
        });

        this._resetListenerAdded = true;
        }
      }

      const data = await this.api.get('/api/asistencias');
      this.renderTable(data);

    } catch (e) {
      document.getElementById('tbody-asistencias').innerHTML =
        `<tr><td colspan="5" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-asistencia');

    data.idCliente = Number(data.idCliente);
    data.idSesion  = Number(data.idSesion);

    try {
      await this.api.post('/api/asistencias/marcar', data);
      this.toast.success('Asistencia registrada correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }
}