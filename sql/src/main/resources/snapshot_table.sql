CREATE TABLE students_snapshot (
	student_name VARCHAR(50) NOT NULL,
	student_surname VARCHAR(50) NOT NULL,
	subject_name VARCHAR(50) NOT NULL,
	mark smallint NOT NULL
);


INSERT INTO students_snapshot (
	SELECT students.name, 
	students.surname, 
	subjects.name, 
	results.mark 
	FROM students 
	LEFT JOIN results ON students.id = results.student_id 
	LEFT JOIN subjects ON results.subject_id = subjects.id
	LIMIT 1000);


/* Additional commands to check result */
SELECT * FROM students_snapshot LIMIT 100;

SELECT students.name,
	students.surname,
	subjects.name,
	results.mark 
	FROM students 
	LEFT JOIN results ON students.id = results.student_id 
	LEFT JOIN subjects ON results.subject_id = subjects.id
	LIMIT 10;

DROP TABLE students_snapshot;