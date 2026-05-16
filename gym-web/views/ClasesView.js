import { Utils } from '../Utils.js';

export class ClasesView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
  }

  async load() {
    try {
      const data = await this.api.get('/api/clases');
      const tb   = document.getElementById('tbody-clases');

      document.getElementById('cnt-clases').textContent = data.length;

      if (!data.length) {
        tb.innerHTML = `<tr><td colspan="3" class="empty-state">Sin registros</td></tr>`;
        return;
      }

      tb.innerHTML = data.map(c => `
        <tr>
          <td><span class="badge badge-yellow">${Utils.or(c.idClase ?? c.id)}</span></td>
          <td>${Utils.or(c.nombreClase)}</td>
          <td style="color:var(--muted); font-size:12px">${Utils.or(c.descripcion)}</td>
        </tr>`).join('');

    } catch (e) {
      document.getElementById('tbody-clases').innerHTML =
        `<tr><td colspan="3" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
    document.getElementById('btn-refresh-clases').onclick = () => this.load();
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-clase');

    try {
      await this.api.post('/api/clases', data);
      this.toast.success('Clase registrada correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }
}
