-- SQL statement for table creation

CREATE TABLE IF NOT EXISTS PEOPLE(Id INTEGER PRIMARY KEY AUTOINCREMENT,
                                 Name TEXT NOT NULL,
                                 Surname TEXT NOT NULL,
                                 Role TEXT NOT NULL);

-- SQL statement for inserting values into the PEOPLE table

INSERT INTO PEOPLE VALUES (1, "Leonardo", "DiCaprio", "CEO");
INSERT INTO PEOPLE VALUES (2, "Scarlett", "Johansson", "CTO");
INSERT INTO PEOPLE VALUES (3, "Tom", "Hanks", "COO");