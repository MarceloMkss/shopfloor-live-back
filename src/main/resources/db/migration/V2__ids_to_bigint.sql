-- Elevar IDs a BIGINT para alinear con entidades Long
ALTER TABLE machine   ALTER COLUMN id TYPE BIGINT;
ALTER TABLE telemetry ALTER COLUMN id TYPE BIGINT;

-- (Opcional) si tienes FK, asegura tipos compatibles. En tu caso telemetry.machine_id ya es BIGINT.
