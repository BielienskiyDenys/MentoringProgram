/* Average mark for input subject name */
SELECT AVG(mark)
FROM results
WHERE subject_id IN (
	SELECT id 
	FROM subjects 
	WHERE name = 'subject_101');