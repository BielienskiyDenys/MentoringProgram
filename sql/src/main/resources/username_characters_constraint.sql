/* Constraint for certain special characters */ 
ALTER TABLE students ADD CONSTRAINT username_characters CHECK (name NOT SIMILAR TO '%(@|#|$)%');

/* Additional commands to check result */
SELECT * FROM students WHERE id = 1;

UPDATE students
SET name = '$tan'
WHERE id = 1;

UPDATE students
SET name = 'Stan'
WHERE id = 1;