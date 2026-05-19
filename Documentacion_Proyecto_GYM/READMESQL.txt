
Base de datos: PostgreSQL 18 | Motor de persistencia: Spring Boot + Hibernate (JPA)

---

## Estructura de archivos

```
gym_scripts/
├── CreateObjects.sql    → DDL: tipos ENUM, tablas, índices
├── InsertData.sql      → DML: datos de prueba realistas
├── ScriptsVarios.sql          → Consultas, vistas, funciones, triggers, mantenimiento
└── README.md               → Este archivo
```

---

## Orden de ejecución

Los scripts **deben ejecutarse en orden**, ya que cada uno depende del anterior.

### Opción 1 — psql desde la terminal

```bash
# 1. Conectarse a PostgreSQL y crear la base de datos (si no existe)
psql -U postgres
CREATE DATABASE gym;
\q

# 2. Ejecutar los scripts en orden
psql -U postgres -d gym -f CreateObjects.sql
psql -U postgres -d gym -f InsertData.sql
psql -U postgres -d gym -f ScriptsVarios.sql
```

### Opción 2 — pgAdmin 4

1. Abrir pgAdmin 4 y conectarse al servidor local.
2. Hacer clic derecho sobre **Databases** → **Create** → **Database** → nombre: `gym`.
3. Seleccionar la base de datos `gym` → abrir el **Query Tool** (ícono de lápiz o Ctrl+Shift+Q).
4. Abrir cada archivo con **File → Open** y ejecutar con **F5**, respetando el orden:
   - Primero `CreateObjects.sql`
   - Luego `InsertData.sql `
   - Finalmente `ScriptsVarios.sql` (selectivamente según lo que necesites)

### Opción 3 — Desde la VM (Debian)

```bash
# Conectarse a PostgreSQL en el host
psql -h 192.168.1.14 -U postgres -d gym -f 01_create_objects.sql
psql -h 192.168.1.14 -U postgres -d gym -f 02_insert_data.sql
psql -h 192.168.1.14 -U postgres -d gym -f 03_varios.sql
```

---

## Descripción de cada script

### `CreateObjects.sql` — Creación de objetos (DDL)

Crea en este orden:

| # | Objeto | Descripción |
|---|--------|-------------|
| 1 | **TIPOs ENUM** | `estado_asistencia`, `estado_factura`, `franquicia_tarjeta`, `tipo_tarjeta` |
| 2 | **cliente** | Clientes del gimnasio |
| 3 | **instructor** | Instructores con especialidad |
| 4 | **plan** | Planes de membresía con duración en días |
| 5 | **clase** | Tipos de clases ofrecidas |
| 6 | **suscripcion** | Relación cliente ↔ plan (con fechas y estado) |
| 7 | **sesion** | Sesiones programadas con clase, instructor y cupos |
| 8 | **factura** | Facturas generadas por suscripciones |
| 9 | **asistencia** | Registro M:N entre cliente y sesión |
| 10 | **pago** | Pago 1:1 asociado a una factura |
| 11 | **efectivo** | Medio de pago: efectivo |
| 12 | **tarjeta** | Medio de pago: tarjeta débito/crédito |
| 13 | **transferencia** | Medio de pago: transferencia bancaria |
| 14 | **Índices** | En llaves foráneas y columnas de búsqueda frecuente |

> **Nota sobre `duracion_dias` en `plan`:** En el MER original el campo aparece como `duracion` (VARCHAR). En el backend (entidad `Plan.java`) está mapeado como `Integer duracionDias`. El script usa `INTEGER` para mantener consistencia con el backend.

---

### `InsertData.sql ` — Datos de prueba (DML)

Inserta datos realistas en el siguiente orden:

| Tabla | Registros | Descripción |
|-------|-----------|-------------|
| cliente | 10 | Nombres colombianos con cédulas ficticias |
| instructor | 5 | Con especialidades variadas |
| plan | 4 | Básico, Estándar, Premium, Anual |
| clase | 5 | Yoga, CrossFit, Spinning, Zumba, Musculación |
| suscripcion | 8 | Mix de estados: ACTIVA, VENCIDA, CANCELADA |
| sesion | 8 | Con fechas, horarios y cupos |
| asistencia | 10 | PRESENTE, TARDANZA y AUSENTE |
| factura | 6 | PAGADA y PENDIENTE |
| pago | 4 | Solo facturas pagadas |
| efectivo | 1 | Pago en efectivo |
| tarjeta | 2 | Visa débito y Mastercard crédito |
| transferencia | 1 | Bancolombia |

---

### `ScriptsVarios.sql` — Scripts varios

Organizado en 5 secciones:

#### Sección A — Consultas de negocio (10 consultas)
| Consulta | Descripción |
|----------|-------------|
| A.1 | Clientes con suscripciones activas |
| A.2 | Sesiones con instructor y cupos disponibles |
| A.3 | Historial de asistencia de un cliente |
| A.4 | Facturas pendientes de pago |
| A.5 | Ingresos totales por mes |
| A.6 | Ingresos desglosados por medio de pago |
| A.7 | Top 5 clases con más asistencias |
| A.8 | Sesiones impartidas por instructor |
| A.9 | Suscripciones próximas a vencer (7 días) |
| A.10 | Clientes con mayor asistencia en el mes actual |

#### Sección B — Vistas (3 vistas)
| Vista | Descripción |
|-------|-------------|
| `vw_suscripciones_activas` | Suscripciones vigentes con días restantes |
| `vw_resumen_caja` | Pagos con medio de pago usado |
| `vw_ocupacion_sesiones` | Porcentaje de ocupación por sesión |

#### Sección C — Funciones (3 funciones)
| Función | Descripción |
|---------|-------------|
| `fn_nombre_cliente(id)` | Retorna nombre completo del cliente |
| `fn_ingresos_periodo(desde, hasta)` | Suma ingresos en un rango de fechas |
| `fn_tiene_suscripcion_activa(id)` | Retorna TRUE si el cliente tiene suscripción vigente |

#### Sección D — Triggers (3 triggers)
| Trigger | Tabla | Descripción |
|---------|-------|-------------|
| `trg_descontar_cupo` | asistencia | Descuenta cupos al registrar asistencia |
| `trg_marcar_factura_pagada` | pago | Marca la factura como PAGADA al registrar el pago |
| `trg_auditoria_suscripcion` | suscripcion | Registra cambios de estado en tabla de auditoría |

#### Sección E — Mantenimiento
- Actualización masiva de suscripciones vencidas
- Consulta de tamaño de tablas
- Reinicio de secuencias
- Scripts de limpieza y DROP (comentados — solo para pruebas)

---

## Relación con el backend (Spring Boot)

Los scripts fueron construidos en base a las entidades JPA del proyecto. La siguiente tabla muestra la correspondencia entre entidad Java y tabla SQL:

| Entidad Java | Tabla SQL | Notas |
|---|---|---|
| `Cliente` | `cliente` | `@OneToMany` con suscripcion y asistencia |
| `Instructor` | `instructor` | `@OneToMany` con sesion |
| `Plan` | `plan` | `duracion_dias` INTEGER (vs VARCHAR en MER original) |
| `Clase` | `clase` | Sin relaciones bidireccionales en la entidad |
| `Suscripcion` | `suscripcion` | `@ManyToOne` con cliente y plan |
| `Sesion` | `sesion` | `@ManyToOne` con clase e instructor; hora como `TIME` |
| `Asistencia` | `asistencia` | Resuelve M:N cliente-sesion; ENUM `estado_asistencia` |
| `Factura` | `factura` | ENUM `estado_factura`; `@ManyToOne` con suscripcion |
| `Pago` | `pago` | `@OneToOne` con factura |
| `Efectivo` | `efectivo` | `@ManyToOne` con pago |
| `Tarjeta` | `tarjeta` | ENUM `franquicia_tarjeta` y `tipo_tarjeta` |
| `Transferencia` | `transferencia` | `@ManyToOne` con pago |

---

## Notas importantes

- El `application.properties` tiene `spring.jpa.hibernate.ddl-auto=update`, lo que significa que Hibernate crea o actualiza las tablas automáticamente al lanzar el backend. Sin embargo, **los ENUMs y los índices adicionales no los genera Hibernate**, por eso es necesario ejecutar `01_create_objects.sql` manualmente antes del primer arranque o en paralelo.
- Los ENUMs de PostgreSQL (`CREATE TYPE`) deben crearse antes que las tablas. Si el backend ya creó las tablas sin ENUMs, ejecutar el script puede generar conflictos. En ese caso, ejecutar primero el bloque de DROP del script E.5 antes de correr el 01.
- Los datos del script `02` son ficticios y están pensados para pruebas. No usar en producción.

---

*Proyecto GYM — Universidad El Bosque — Ingeniería de Sistemas*