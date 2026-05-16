import { Utils } from '../Utils.js';

export class FacturasView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
    this._allFacturas = [];
  }

  _renderTable(data) {
    const tb = document.getElementById('tbody-facturas');
    document.getElementById('cnt-facturas').textContent = data.length;

    if (!data.length) {
        tb.innerHTML = `<tr><td colspan="5" class="empty-state">Sin registros</td></tr>`;
        return;
    }

    tb.innerHTML = data.map(f => {
        const est = f.estado ?? 'PENDIENTE';

        const cls =
        est === 'PAGADA'
            ? 'badge-green'
            : est === 'PENDIENTE'
            ? 'badge-yellow'
            : 'badge-red';

        return `
        <tr>
            <td><span class="badge badge-yellow">${Utils.or(f.id)}</span></td>
            <td>${Utils.or(f.idSuscripcion)}</td>
            <td style="color:var(--success)">
              ${Utils.formatCOP(f.total)}
            </td>
            <td>${Utils.or(f.fechaEmision, '—')}</td>
            <td><span class="badge ${cls}">${est}</span></td>
        </tr>
        `;
        }).join('');
    }

  async load() {
    try {
      const suscrip = await this.api.get('/api/suscripciones');

      Utils.populateSelect(
        'sel-suscr-factura',
        suscrip,
        s => s.idSuscripcion ?? s.id,
        s => `ID ${s.idSuscripcion ?? s.id} - ${s.cliente?.primerNombre ?? ''} ${s.cliente?.primerApellido ?? ''} [${s.plan?.nombrePlan ?? ''}]`
      );

      const data = await this.api.get('/api/facturas');
      this._allFacturas = data;
      this._renderTable(data);

      const csFactura = document.getElementById('cs-filter-factura');
        if (csFactura && !csFactura._listenerAdded) {
        csFactura.addEventListener('cs-change', async (e) => {
            const val = e.detail.value;
            if (val === 'todos') {
            this._renderTable(this._allFacturas);
            } else {
            try {
                const filtradas = await this.api.get(`/api/facturas/estado/${val}`);
                this._renderTable(filtradas);
            } catch {
                const filtradas = this._allFacturas.filter(f => (f.estado ?? 'PENDIENTE') === val);
                this._renderTable(filtradas);
            }
            }
        });
        csFactura._listenerAdded = true;
        }

      const inputSearch = document.getElementById('input-search-factura');
      if (inputSearch && !inputSearch._listenerAdded) {
        inputSearch.addEventListener('input', () => {
          const q = inputSearch.value.toLowerCase().trim();
          const filtradas = this._allFacturas.filter(f =>
            String(f.idFactura ?? f.id).includes(q) ||
            String(f.suscripcion?.idSuscripcion ?? f.idSuscripcion).includes(q) ||
            (f.estado ?? '').toLowerCase().includes(q) ||
            (f.fechaEmision ?? '').includes(q)
          );
          this._renderTable(filtradas);
        });
        inputSearch._listenerAdded = true;
      }

    } catch (e) {
      document.getElementById('tbody-facturas').innerHTML =
        `<tr><td colspan="5" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  async submit(event) {
    event.preventDefault();
    const data = Utils.getFormData('form-factura');

    const rawId = data.idPago ?? data.idSuscripcion;
    const payload = {
      idSuscripcion: Number(rawId),
      total:  parseFloat(data.total),
      fechaEmision:  data.fechaEmision || null,
    };

    try {
      await this.api.post('/api/facturas', payload);
      this.toast.success('Factura emitida correctamente');
      event.target.reset();
      await this.load();
    } catch (err) {
      this.toast.error(err.message);
    }
  }
}