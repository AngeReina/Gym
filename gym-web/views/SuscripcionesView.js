import { Utils } from '../Utils.js';

export class SuscripcionesView {
    constructor(api, toast) {
        this.api   = api;
        this.toast = toast;
        this._allSuscr = [];
    }

    _renderTable(data) {
        const tb = document.getElementById('tbody-suscripciones');
        document.getElementById('cnt-suscrip').textContent = data.length;

        if (!data.length) {
            tb.innerHTML = `<tr><td colspan="6" class="empty-state">Sin registros</td></tr>`;
            return;
        }

        tb.innerHTML = data.map(s => {
            const est = s.estado ?? 'ACTIVO';
            const cls = est === 'ACTIVO'  ? 'badge-green'
                      : est === 'VENCIDO' ? 'badge-red'
                      : 'badge-yellow';
            return `
            <tr>
                <td><span class="badge badge-yellow">${Utils.or(s.id)}</span></td>
                <td>${Utils.or(s.clienteNombre)}</td>
                <td>${Utils.or(s.planNombre)}</td>
                <td style="font-size:12px">${Utils.or(s.inicio)}</td>
                <td style="font-size:12px">${Utils.or(s.fin)}</td>
                <td><span class="badge ${cls}">${est}</span></td>
            </tr>`;
        }).join('');
    }

    async load() {
        try {
            const [clientes, planes] = await Promise.all([
                this.api.get('/api/clientes'),
                this.api.get('/api/planes'),
            ]);

            Utils.populateSelect('sel-cliente-suscr', clientes,
                c => c.idCliente ?? c.id,
                c => `${c.primerNombre ?? ''} ${c.primerApellido ?? ''}`
            );

            Utils.populateSelect('sel-plan-suscr', planes,
                p => p.idPlan ?? p.id,
                p => `${p.nombrePlan} - ${Utils.formatCOP(p.costo)}`
            );

            const data = await this.api.get('/api/suscripciones');
            this._allSuscr = data;
            this._renderTable(data);

            const csEstado = document.getElementById('cs-suscrip-estado');
            if (csEstado && !csEstado._listenerAdded) {
                csEstado.addEventListener('cs-change', () => this._applyFilters());
                csEstado._listenerAdded = true;
            }

        } catch (e) {
            document.getElementById('tbody-suscripciones').innerHTML =
                `<tr><td colspan="6" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
        }
    }

    async _applyFilters() {
        const estado = document.querySelector('#cs-suscrip-estado-menu .custom-select-option.selected')?.dataset.value ?? '';

        try {
            const data = estado
                ? await this.api.get(`/api/suscripciones/estado/${estado}`)
                : this._allSuscr;
            this._renderTable(data);
        } catch {
            const data = estado
                ? this._allSuscr.filter(s => (s.estado ?? 'ACTIVO') === estado)
                : this._allSuscr;
            this._renderTable(data);
        }
    }

    async submit(event) {
        event.preventDefault();
        const data = Utils.getFormData('form-suscripcion');

        data.idCliente = Number(data.idCliente);
        data.idPlan    = Number(data.idPlan);

        try {
            await this.api.post('/api/suscripciones', data);
            this.toast.success('Suscripcion creada correctamente');
            event.target.reset();
            await this.load();
        } catch (err) {
            this.toast.error(err.message);
        }
    }
}