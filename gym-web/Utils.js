export class Utils {
  /**
   * @param {string} formId
   * @returns {Object}
   */
  static getFormData(formId) {
    const fd = new FormData(document.getElementById(formId));
    const obj = {};
    fd.forEach((value, key) => {
      obj[key] = value === '' ? null : value;
    });
    return obj;
  }

  /**
   * Rellena un <select> con un array de datos.
   * @param {string}   selId    
   * @param {Array}    data     
   * @param {Function} valFn   
   * @param {Function} labelFn 
   */
  static populateSelect(selId, data, valFn, labelFn) {
    const sel = document.getElementById(selId);
    if (!sel) return;

    const current = sel.value;
    sel.innerHTML = '<option value="">Seleccionar...</option>';

    (data ?? []).forEach(item => {
      const opt = document.createElement('option');
      opt.value = valFn(item);
      opt.textContent = labelFn(item);
      sel.appendChild(opt);
    });

    if (current) sel.value = current;
  }

  /**
   * Formatea un numero como moneda colombiana.
   * @param {number} value
   * @returns {string}
   */
  static formatCOP(value) {
    return `$${Number(value ?? 0).toLocaleString('es-CO')}`;
  }

  /**
   * Devuelve el valor con fallback.
   * @param {*} value
   * @param {string} fallback
   * @returns {string}
   */
  static or(value, fallback = '—') {
    return value ?? fallback;
  }
}
