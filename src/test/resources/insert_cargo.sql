ALTER TABLE cargo ALTER COLUMN id SET DEFAULT 0;

INSERT INTO cargo (id, weight, weight_unit, pieces, flight_id)
VALUES (1, 6.18, 'POUND', 12, 3);
INSERT INTO cargo (id, weight, weight_unit, pieces, flight_id)
VALUES (2, 8.62, 'POUND', 51, 3);
INSERT INTO cargo (id, weight, weight_unit, pieces, flight_id)
VALUES (3, 0.18, 'KILOGRAM', 15, 3);

INSERT INTO cargo (id, weight, weight_unit, pieces, flight_id)
VALUES (4, 0.43, 'POUND', 23, 4);
INSERT INTO cargo (id, weight, weight_unit, pieces, flight_id)
VALUES (5, 0.68, 'KILOGRAM', 36, 4);