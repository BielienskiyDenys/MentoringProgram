/* Students at 'red zone' */
SELECT foo.name, foo.surname FROM (
	SELECT COUNT(results.id)  AS red_zone_marks, students.name, students.surname
	FROM results
	LEFT JOIN students ON results.student_id = students.id
	WHERE mark<30 /*red zone threshold*/
	GROUP BY student_id, students.name, students.surname
) AS foo WHERE red_zone_marks > 2 /*red zone marks count*/
LIMIT 1000;

/* Querry without sub-table */
SELECT students.name, students.surname, SUM(1)
FROM students 
INNER JOIN results on results.student_id = students.id
WHERE mark <= 30
GROUP BY (students.id)
HAVING SUM(1) > 6
LIMIT 1000;

/* count of marks less than X for each student */
SELECT COUNT(results.id) AS red_zone_marks, students.name, students.surname
FROM results LEFT JOIN students ON results.student_id = students.id
WHERE mark<30
GROUP BY student_id, students.name, students.surname
LIMIT 100;


/*manual check from */
SELECT * FROM results LIMIT 100;