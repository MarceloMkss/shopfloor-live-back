-- Tabla para almacenar la información de las máquinas
CREATE TABLE IF NOT EXISTS machine (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  vendor VARCHAR(100) NOT NULL,
  model VARCHAR(100) NOT NULL,
  status VARCHAR(20) NOT NULL
);

-- Tabla para almacenar la telemetría de las máquinas
CREATE TABLE IF NOT EXISTS telemetry (
  id SERIAL PRIMARY KEY,
  machine_id BIGINT NOT NULL,
  ts TIMESTAMPTZ NOT NULL,
  spindle_speed INT,
  feed_rate INT,
  part_count INT,
  alarm_code VARCHAR(50), -- Nombre de columna corregido para ser consistente
  temperature DOUBLE PRECISION
);

-- Índice para optimizar las consultas por máquina y tiempo
CREATE INDEX IF NOT EXISTS idx_telemetry_machine_ts ON telemetry(machine_id, ts);