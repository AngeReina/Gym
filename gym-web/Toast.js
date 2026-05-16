export class ToastService {
  constructor(containerId = 'toast-container') {
    this.container = document.getElementById(containerId);
  }

  show(msg, type = 'success') {
    const el = document.createElement('div');
    el.className = `toast toast-${type}`;
    el.innerHTML = `<span>${type === 'success' ? 'OK' : 'ERR'}</span> ${msg}`;
    this.container.appendChild(el);
    setTimeout(() => el.remove(), 3500);
  }

  success(msg) { this.show(msg, 'success'); }
  error(msg)   { this.show(msg, 'error'); }
}
