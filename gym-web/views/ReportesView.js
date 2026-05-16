import { Utils } from '../Utils.js';

/**
 * ReportesView
 * Expone en el front todas las consultas analíticas/agregadas del backend:
 *  - PagoRepository:     distribucionMetodosPago, rankingClientesPagos, totalRecaudado, totalPorFactura
 *  - EfectivoRepository: totalEfectivoPorCliente, totalEfectivo
 *  - TarjetaRepository:  usoTarjetasPorFranquicia, promedioPorFranquicia, clientesTopTarjeta
 *  - TransferenciaRepository: rankingBancosPorDinero, usoTransferenciasPorBanco, promedioPorBanco
 *  - SuscripcionRepository:   planesMasUsados, contarActivas
 *  - FacturaRepository:       totalFacturadoPagado, contarFacturasPagadas
 */
export class ReportesView {
  constructor(api, toast) {
    this.api   = api;
    this.toast = toast;
  }

  async load() {
    await Promise.allSettled([
      this._loadDistribucionMetodos(),
      this._loadRankingClientes(),
      this._loadTotalesMetodos(),
      this._loadRankingBancos(),
      this._loadFranquicias(),
      this._loadPlanesMasUsados(),
      this._loadResumenFacturas(),
    ]);
  }

  async _loadDistribucionMetodos() {
    const el = document.getElementById('rep-distribucion-metodos');
    if (!el) return;
    try {
      const data = await this.api.get('/api/reportes/distribucion-metodos');
      if (!data?.length) { el.innerHTML = '<p class="rep-empty">Sin datos</p>'; return; }

      const total = data.reduce((s, r) => s + Number(r[1] ?? 0), 0);
      el.innerHTML = data.map(([metodo, cnt]) => {
        const pct = total ? Math.round((Number(cnt) / total) * 100) : 0;
        const color = metodo === 'EFECTIVO' ? 'var(--success)'
                    : metodo === 'TARJETA'  ? 'var(--accent)'
                    : 'var(--accent2)';
        return `
          <div class="rep-bar-row">
            <span class="rep-bar-label">${metodo}</span>
            <div class="rep-bar-track">
              <div class="rep-bar-fill" style="width:${pct}%; background:${color}"></div>
            </div>
            <span class="rep-bar-val">${cnt} <span style="color:var(--muted);font-size:11px">(${pct}%)</span></span>
          </div>`;
      }).join('');
    } catch (e) {
      el.innerHTML = `<p class="rep-empty rep-err">${e.message}</p>`;
    }
  }

  async _loadRankingClientes() {
    const tb = document.getElementById('tbody-ranking-clientes');
    if (!tb) return;
    try {
      const data = await this.api.get('/api/reportes/ranking-clientes');
      if (!data?.length) { tb.innerHTML = `<tr><td colspan="3" class="empty-state">Sin datos</td></tr>`; return; }

      tb.innerHTML = data.map(([id, nombre, total], i) => `
        <tr>
          <td><span class="badge ${i === 0 ? 'badge-green' : i === 1 ? 'badge-yellow' : 'badge-orange'}">#${i + 1}</span></td>
          <td>${Utils.or(nombre)}</td>
          <td style="color:var(--success);font-weight:600">${Utils.formatCOP(total)}</td>
        </tr>`).join('');
    } catch (e) {
      tb.innerHTML = `<tr><td colspan="3" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  async _loadTotalesMetodos() {
    const el = document.getElementById('rep-totales-metodos');
    if (!el) return;
    try {
      const [totEfectivo, totTarjeta, totTransfer, totRecaudado] = await Promise.allSettled([
        this.api.get('/api/reportes/total-efectivo'),       // Double
        this.api.get('/api/reportes/total-tarjetas'),       // Double
        this.api.get('/api/reportes/total-transferencias'), // Double
        this.api.get('/api/reportes/total-recaudado'),      // Double
      ]);

      const safe = r => r.status === 'fulfilled' ? (r.value ?? 0) : 0;

      el.innerHTML = `
        <div class="rep-kpi-grid">
          <div class="rep-kpi">
            <div class="rep-kpi-label">Efectivo</div>
            <div class="rep-kpi-val" style="color:var(--success)">${Utils.formatCOP(safe(totEfectivo))}</div>
          </div>
          <div class="rep-kpi">
            <div class="rep-kpi-label">Tarjetas</div>
            <div class="rep-kpi-val" style="color:var(--accent)">${Utils.formatCOP(safe(totTarjeta))}</div>
          </div>
          <div class="rep-kpi">
            <div class="rep-kpi-label">Transferencias</div>
            <div class="rep-kpi-val" style="color:var(--accent2)">${Utils.formatCOP(safe(totTransfer))}</div>
          </div>
          <div class="rep-kpi rep-kpi-total">
            <div class="rep-kpi-label">Total Recaudado</div>
            <div class="rep-kpi-val">${Utils.formatCOP(safe(totRecaudado))}</div>
          </div>
        </div>`;
    } catch (e) {
      el.innerHTML = `<p class="rep-empty rep-err">${e.message}</p>`;
    }
  }

  async _loadRankingBancos() {
    const tb = document.getElementById('tbody-ranking-bancos');
    if (!tb) return;
    try {
      const data = await this.api.get('/api/reportes/ranking-bancos');
      if (!data?.length) { tb.innerHTML = `<tr><td colspan="2" class="empty-state">Sin transferencias</td></tr>`; return; }

      tb.innerHTML = data.map(([banco, total]) => `
        <tr>
          <td>${Utils.or(banco)}</td>
          <td style="color:var(--accent2);font-weight:600">${Utils.formatCOP(total)}</td>
        </tr>`).join('');
    } catch (e) {
      tb.innerHTML = `<tr><td colspan="2" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  async _loadFranquicias() {
    const tb = document.getElementById('tbody-franquicias');
    if (!tb) return;
    try {
      const data = await this.api.get('/api/reportes/franquicias');
      if (!data?.length) { tb.innerHTML = `<tr><td colspan="2" class="empty-state">Sin datos</td></tr>`; return; }

      tb.innerHTML = data.map(([franq, cnt]) => `
        <tr>
          <td><span class="badge badge-yellow">${Utils.or(franq)}</span></td>
          <td>${Utils.or(cnt)} transacciones</td>
        </tr>`).join('');
    } catch (e) {
      tb.innerHTML = `<tr><td colspan="2" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  async _loadPlanesMasUsados() {
    const tb = document.getElementById('tbody-planes-usados');
    if (!tb) return;
    try {
      const data = await this.api.get('/api/reportes/planes-mas-usados');
      if (!data?.length) { tb.innerHTML = `<tr><td colspan="2" class="empty-state">Sin datos</td></tr>`; return; }

      tb.innerHTML = data.map(([plan, total]) => `
        <tr>
          <td>${Utils.or(plan)}</td>
          <td style="color:var(--accent);font-weight:600">${Utils.or(total)} suscripciones</td>
        </tr>`).join('');
    } catch (e) {
      tb.innerHTML = `<tr><td colspan="2" class="empty-state" style="color:var(--danger)">${e.message}</td></tr>`;
    }
  }

  async _loadResumenFacturas() {
    const el = document.getElementById('rep-resumen-facturas');
    if (!el) return;
    try {
      const [pagadas, totalPagado] = await Promise.allSettled([
        this.api.get('/api/reportes/facturas-pagadas-count'),  // Long
        this.api.get('/api/reportes/facturas-pagadas-total'),  // Double
      ]);

      const safe = r => r.status === 'fulfilled' ? (r.value ?? 0) : 0;

      el.innerHTML = `
        <div class="rep-kpi-grid rep-kpi-grid-2">
          <div class="rep-kpi">
            <div class="rep-kpi-label">Facturas Pagadas</div>
            <div class="rep-kpi-val" style="color:var(--success)">${safe(pagadas)}</div>
          </div>
          <div class="rep-kpi">
            <div class="rep-kpi-label">Total Cobrado</div>
            <div class="rep-kpi-val" style="color:var(--success)">${Utils.formatCOP(safe(totalPagado))}</div>
          </div>
        </div>`;
    } catch (e) {
      el.innerHTML = `<p class="rep-empty rep-err">${e.message}</p>`;
    }
  }
}