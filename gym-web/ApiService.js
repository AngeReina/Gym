export class ApiService {
  constructor(baseUrl = 'http://192.168.1.50:8080') {
    this.baseUrl = baseUrl;
  }

  async request(path, method = 'GET', body = null) {
    const opts = {
      method,
      headers: { 'Content-Type': 'application/json' },
    };

    if (body) opts.body = JSON.stringify(body);

    const res = await fetch(this.baseUrl + path, opts);

    if (!res.ok) {
      const txt = await res.text().catch(() => res.statusText);
      throw new Error(txt || `HTTP ${res.status}`);
    }

    const ct = res.headers.get('content-type') || '';
    if (ct.includes('application/json')) return res.json();
    return res.text();
  }

  get(path)              { return this.request(path, 'GET'); }
  post(path, body)       { return this.request(path, 'POST', body); }
  put(path, body)        { return this.request(path, 'PUT', body); }
  delete(path)           { return this.request(path, 'DELETE'); }
}
