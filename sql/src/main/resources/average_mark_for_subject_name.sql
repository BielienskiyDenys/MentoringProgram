/* Average mark for input subject name */
SELECT AVG(mark)
FROM results
WHERE subject_id IN (
	SELECT id 
	FROM subjects 
	WHERE name = 'subject_101');

	
SELECT subjects.name, AVG(results.mark) FROM subjects 
INNER JOIN results on results.subject_id = subjects.id
GROUP BY subjects.name
HAVING subjects.name = 'test_subject'; 
