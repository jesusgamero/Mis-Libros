--
-- File generated with SQLiteStudio v3.1.1 on ju. ene. 26 16:21:48 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: libros
CREATE TABLE libros (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo VARCHAR (35), autor VARCHAR (25), editorial VARCHAR (25), isbn VARCHAR (10), anio INT, paginas INT, ebook BOOLEAN, leido BOOLEAN, nota DOUBLE, resumen TEXT);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
