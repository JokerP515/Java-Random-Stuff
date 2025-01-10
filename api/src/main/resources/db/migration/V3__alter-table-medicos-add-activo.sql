ALTER TABLE medicos add activo boolean not null;
UPDATE medicos SET activo = true;