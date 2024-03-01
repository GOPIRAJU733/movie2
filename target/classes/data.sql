INSERT INTO movielist (movieName, leadActor)
SELECT 'Avengers: Endgame', 'Robert Downey Jr.'
WHERE NOT EXISTS (SELECT 1 FROM movielist WHERE movieId = 1);

INSERT INTO movielist (movieName, leadActor)
SELECT 'Avatar', 'Sam Worthington'
WHERE NOT EXISTS (SELECT 2 FROM movielist WHERE movieId = 2);

INSERT INTO movielist (movieName, leadActor)
SELECT 'Titanic', 'Leonardo DiCaprio'
WHERE NOT EXISTS (SELECT 3 FROM movielist WHERE movieId = 3);

INSERT INTO movielist (movieName, leadActor)
SELECT 'Star Wars: The Force Awakens', 'Daisy Ridley'
WHERE NOT EXISTS (SELECT 4 FROM movielist WHERE movieId = 4);

INSERT INTO movielist (movieName, leadActor)
SELECT 'Jurassic World', 'Chris Pratt'
WHERE NOT EXISTS (SELECT 5 FROM movielist WHERE movieId = 5);

