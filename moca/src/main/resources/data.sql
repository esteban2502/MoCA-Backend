-- Categorías iniciales para MoCA (se ejecuta al arrancar si la tabla existe).
-- ON CONFLICT evita duplicados si el nombre ya existe.
INSERT INTO categories (name) VALUES
('FUNCIÓN EJECUTIVA'),
('MEMORIA INMEDIATA'),
('FLUIDEZ'),
('ORIENTACIÓN'),
('CÁLCULO'),
('ABSTRACCIÓN'),
('MEMORIA DIFERIDA'),
('PERCEPCIÓN VISUAL'),
('DENOMINACIÓN'),
('ATENCIÓN'),
('VISUOESPACIAL / EJECUTIVA'),
('IDENTIFICACIÓN'),
('MEMORIA'),
('LENGUAJE')
ON CONFLICT (name) DO NOTHING;
