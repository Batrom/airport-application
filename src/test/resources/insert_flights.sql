ALTER TABLE flight ALTER COLUMN id SET DEFAULT 0;

INSERT INTO flight (id, number, departure_airport_id, arrival_airport_id, departure_date)
VALUES (1, 1, 1, 2, DATEADD('DAY', -7, CURRENT_DATE));
INSERT INTO flight (id, number, departure_airport_id, arrival_airport_id, departure_date)
VALUES (2, 2, 2, 3, DATEADD('DAY', -5, CURRENT_DATE));
INSERT INTO flight (id, number, departure_airport_id, arrival_airport_id, departure_date)
VALUES (3, 3, 3, 4, DATEADD('DAY', -3, CURRENT_DATE));
INSERT INTO flight (id, number, departure_airport_id, arrival_airport_id, departure_date)
VALUES (4, 4, 4, 5, DATEADD('DAY', -1, CURRENT_DATE));
INSERT INTO flight (id, number, departure_airport_id, arrival_airport_id, departure_date)
VALUES (5, 5, 5, 4, DATEADD('DAY', -10, CURRENT_DATE));
INSERT INTO flight (id, number, departure_airport_id, arrival_airport_id, departure_date)
VALUES (6, 6, 4, 5, DATEADD('DAY', -10, CURRENT_DATE));