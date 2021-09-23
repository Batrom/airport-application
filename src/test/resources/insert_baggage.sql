ALTER TABLE baggage ALTER COLUMN id SET DEFAULT 0;

INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (1, 1.32, 'KILOGRAM', 2, 2);
INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (2, 2.93, 'KILOGRAM', 5, 2);
INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (3, 0.78, 'POUND', 1, 2);

INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (4, 0.85, 'KILOGRAM', 2, 4);
INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (5, 0.36, 'KILOGRAM', 3, 4);

INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (6, 12.32, 'KILOGRAM', 22, 5);
INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (7, 22.35, 'POUND', 13, 6);
INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (8, 12.32, 'KILOGRAM', 0, 5);
INSERT INTO baggage (id, weight, weight_unit, pieces, flight_id)
VALUES (9, 22.35, 'POUND', 163, 6);