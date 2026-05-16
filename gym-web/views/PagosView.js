import { Utils } from '../Utils.js';

export class PagosView {
  constructor(api, toast) {
    this.api          = api;
    this.toast        = toast;
    this.metodoCount  = 0;
  }

  async load() {
    try {
      const facturas   = await this.api.get('/api/facturas');
      const pendientes = facturas.filter(f => (f.estado ?? '') !== 'PAGADA');
      const lista      = pendientes.length ? pendientes : facturas;

      Utils.populateSelect(
        'sel-factura-pago',
        lista,
        f => f.idFactura ?? f.id,
        f => `Factura ${f.idFactura ?? f.id} - ${Utils.formatCOP(f.totalFactura)} [${f.estado ?? ''}]`
      );
    } catch (e) {
      this.toast.error('No se pudieron cargar facturas: ' + e.message);
    }

    const container = document.getElementById('metodos-container');
    if (container && container.children.length === 0) {
      this.addMetodo();
    }
  }

  addMetodo() {
    this.metodoCount++;
    const id  = 'metodo-' + this.metodoCount;
    const div = document.createElement('div');
    div.className = 'metodo-row';
    div.id        = id;

    div.innerHTML = `
      <div class="metodo-header">
        <select class="tipo-metodo-sel"
          style="background:var(--surface);border:1px solid var(--border);border-radius:6px;
                 color:var(--text);padding:6px 10px;font-family:var(--font-body);
                 font-size:13px;outline:none;appearance:none;"
          data-metodo-id="${id}">
          <option value="EFECTIVO">Efectivo</option>
          <option value="TARJETA">Tarjeta</option>
          <option value="TRANSFERENCIA">Transferencia</option>
        </select>
        <button type="button" class="btn btn-danger btn-sm" data-remove-id="${id}">
          Eliminar
        </button>
      </div>
      <div class="metodo-fields" id="${id}-fields">
        <div class="metodo-field">
          <label>Monto</label>
          <input type="number" step="0.01" placeholder="0.00" class="met-monto"/>
        </div>
      </div>`;

    document.getElementById('metodos-container').appendChild(div);

    div.querySelector('.tipo-metodo-sel').addEventListener('change', (e) => {
      this.updateMetodoFields(id, e.target.value);
    });

    div.querySelector('[data-remove-id]').addEventListener('click', () => {
      this.removeMetodo(id);
    });
  }

  removeMetodo(id) {
    const el = document.getElementById(id);
    if (el) el.remove();
  }

  updateMetodoFields(id, tipo) {
    const container = document.getElementById(id + '-fields');
    let extra = '';

    if (tipo === 'TARJETA') {
      extra = `
        <div class="metodo-field">
          <label>Franquicia</label>
          <select class="met-franquicia"
            style="background:var(--surface);border:1px solid var(--border);border-radius:6px;
                   color:var(--text);padding:6px 10px;font-family:var(--font-body);
                   font-size:13px;outline:none;width:100%;appearance:none;">
            <option value="VISA">VISA</option>
            <option value="MASTERCARD">MASTERCARD</option>
            <option value="AMERICAN_EXPRESS">AMERICAN EXPRESS</option>
          </select>
        </div>
        <div class="metodo-field">
          <label>Tipo</label>
          <select class="met-tipo-tarjeta"
            style="background:var(--surface);border:1px solid var(--border);border-radius:6px;
                   color:var(--text);padding:6px 10px;font-family:var(--font-body);
                   font-size:13px;outline:none;width:100%;appearance:none;">
            <option value="DEBITO">DEBITO</option>
            <option value="CREDITO">CREDITO</option>
          </select>
        </div>`;

    } else if (tipo === 'TRANSFERENCIA') {
      extra = `
        <div class="metodo-field">
          <label>Banco Origen</label>
          <input type="text" placeholder="Bancolombia" class="met-banco"/>
        </div>
        <div class="metodo-field">
          <label>Num. Transaccion</label>
          <input type="text" placeholder="TXN123456" class="met-num-tx"/>
        </div>`;
    }

    container.innerHTML = `
      <div class="metodo-field">
        <label>Monto</label>
        <input type="number" step="0.01" placeholder="0.00" class="met-monto"/>
      </div>${extra}`;
  }

  _collectMetodos() {
    const metodos = [];

    document.querySelectorAll('.metodo-row').forEach(row => {
      const tipo  = row.querySelector('.tipo-metodo-sel')?.value ?? 'EFECTIVO';
      const monto = parseFloat(row.querySelector('.met-monto')?.value || 0);
      const entry = { tipoMetodo: tipo, monto };

      if (tipo === 'TARJETA') {
        entry.franquicia  = row.querySelector('.met-franquicia')?.value ?? null;
        entry.tipoTarjeta = row.querySelector('.met-tipo-tarjeta')?.value ?? null;

      } else if (tipo === 'TRANSFERENCIA') {
        entry.bancoOrigen    = row.querySelector('.met-banco')?.value ?? null;
        entry.numTransaccion = row.querySelector('.met-num-tx')?.value ?? null;
      }

      metodos.push(entry);
    });

    return metodos;
  }

  async submit(event) {
  event.preventDefault();

  const d = Utils.getFormData('form-pago');

  const payload = {
    idFactura: Number(d.idFactura),
    totalRecibo: parseFloat(d.totalRecibo),
    metodos: this._collectMetodos(),
  };

  try {
    await this.api.post('/api/pagos', payload);

    this.toast.success('Pago registrado y factura actualizada');

    event.target.reset();

    document.getElementById('metodos-container').innerHTML = '';
    this.metodoCount = 0;

    this.addMetodo();

    await this.load();

  } catch (err) {
    this.toast.error(err.message);
  }
}
}
