-- ============================================
-- PoliBank - Esquema SQLite
-- ============================================

PRAGMA foreign_keys = ON;

-- Tabla de usuarios (credenciales para Basic Auth)
CREATE TABLE usuario (
    username    TEXT PRIMARY KEY,
    password    TEXT NOT NULL,          -- hash (BCrypt), nunca texto plano
    nombre      TEXT NOT NULL,
    rol         TEXT NOT NULL DEFAULT 'USER',  -- para Spring Security (ROLE_USER, etc.)
    fecha_alta  TEXT NOT NULL DEFAULT (datetime('now'))
);

-- Tabla de cuentas (1 a 1 con usuario para este alcance)
CREATE TABLE cuenta (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    username        TEXT NOT NULL UNIQUE,
    saldo           INTEGER NOT NULL DEFAULT 0 CHECK (saldo >= 0),
    fecha_creacion  TEXT NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (username) REFERENCES usuario(username) ON DELETE CASCADE
);

-- Tabla de transferencias (la operación en sí, entre dos cuentas)
CREATE TABLE transferencia (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    cuenta_origen_id    INTEGER NOT NULL,
    cuenta_destino_id   INTEGER NOT NULL,
    monto               INTEGER NOT NULL CHECK (monto > 0),
    fecha               TEXT NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (cuenta_origen_id)  REFERENCES cuenta(id),
    FOREIGN KEY (cuenta_destino_id) REFERENCES cuenta(id),
    CHECK (cuenta_origen_id <> cuenta_destino_id)
);

-- Tabla de movimientos (lo que ve el usuario en su historial: 1 fila por cuenta afectada)
-- Cada transferencia genera 2 movimientos: un DEBITO (origen) y un CREDITO (destino)
CREATE TABLE movimiento (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    cuenta_id           INTEGER NOT NULL,
    transferencia_id    INTEGER NOT NULL,
    tipo                TEXT NOT NULL CHECK (tipo IN ('DEBITO', 'CREDITO')),
    monto               INTEGER NOT NULL CHECK (monto > 0),
    saldo_posterior     INTEGER NOT NULL,     -- saldo de la cuenta luego de aplicar el movimiento
    fecha               TEXT NOT NULL DEFAULT (datetime('now')),
    FOREIGN KEY (cuenta_id)        REFERENCES cuenta(id),
    FOREIGN KEY (transferencia_id) REFERENCES transferencia(id) ON DELETE CASCADE
);
