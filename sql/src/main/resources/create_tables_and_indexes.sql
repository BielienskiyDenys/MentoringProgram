CREATE DATABASE module07;

\c module07;

CREATE TABLE students (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL,
surname VARCHAR(50) NOT NULL,
date_of_birth DATE,
phone_numbers VARCHAR(100),
primary_skill VARCHAR(50) NOT NULL,
created_datetime TIMESTAMP NOT NULL DEFAULT NOW(),
updated_datetime TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE subjects (
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL,
tutor VARCHAR(50) NOT NULL
);

CREATE TABLE results (
id SERIAL PRIMARY KEY,
student_id int4 NOT NULL REFERENCES students(id),
subject_id int4 NOT NULL REFERENCES subjects(id),
mark smallint NOT NULL
);

CREATE INDEX IF NOT EXISTS btree_index_name_stud ON students USING BTREE(name);
CREATE INDEX IF NOT EXISTS btree_index_surname_stud ON students USING BTREE(surname);
CREATE INDEX IF NOT EXISTS btree_index_skill_stud ON students USING BTREE(primary_skill);
CREATE INDEX IF NOT EXISTS btree_index_subj ON subjects USING BTREE(name);
CREATE INDEX IF NOT EXISTS btree_index_r ON results USING BTREE(student_id, subject_id, mark);

CREATE INDEX IF NOT EXISTS hash_index_surname_stud ON students USING HASH(surname);
CREATE INDEX IF NOT EXISTS hash_index_name_stud ON students USING HASH(name);
CREATE INDEX IF NOT EXISTS hash_index_primary_skill_stud ON students USING HASH(primary_skill);
CREATE INDEX IF NOT EXISTS hash_index_name_subj ON subjects USING HASH(name);
CREATE INDEX IF NOT EXISTS hash_index_stu_r ON results USING HASH(student_id);
CREATE INDEX IF NOT EXISTS hash_index_subj_r ON results USING HASH(subject_id);
CREATE INDEX IF NOT EXISTS hash_index_mark_r ON results USING HASH(mark);

CREATE INDEX IF NOT EXISTS gin_index_name_stud ON students USING GIN(to_tsvector('english'::regconfig, name));
CREATE INDEX IF NOT EXISTS gin_index_surname_stud ON students USING GIN(to_tsvector('english'::regconfig, surname));
CREATE INDEX IF NOT EXISTS gin_index_skill_stud ON students USING GIN(to_tsvector('english'::regconfig, primary_skill));
CREATE INDEX IF NOT EXISTS gin_index_subj ON subjects USING GIN(to_tsvector('english'::regconfig, name));

CREATE INDEX IF NOT EXISTS gist_index_name_stud ON students USING GIST(to_tsvector('english'::regconfig, name));
CREATE INDEX IF NOT EXISTS gist_index_surname_stud ON students USING GIST(to_tsvector('english'::regconfig, surname));
CREATE INDEX IF NOT EXISTS gist_index_skill_stud ON students USING GIST(to_tsvector('english'::regconfig, primary_skill));
CREATE INDEX IF NOT EXISTS gist_index_subj ON subjects USING GIST(to_tsvector('english'::regconfig, name));

DROP INDEX IF EXISTS btree_index_name_stud;
DROP INDEX IF EXISTS btree_index_surname_stud;
DROP INDEX IF EXISTS btree_index_skill_stud;
DROP INDEX IF EXISTS btree_index_subj;
DROP INDEX IF EXISTS btree_index_r;

DROP INDEX IF EXISTS hash_index_surname_stud;
DROP INDEX IF EXISTS hash_index_name_stud;
DROP INDEX IF EXISTS hash_index_primary_skill_stud;
DROP INDEX IF EXISTS hash_index_name_subj;
DROP INDEX IF EXISTS hash_index_stu_r;
DROP INDEX IF EXISTS hash_index_subj_r;
DROP INDEX IF EXISTS hash_index_mark_r;

DROP INDEX IF EXISTS gin_index_name_stud;
DROP INDEX IF EXISTS gin_index_surname_stud;
DROP INDEX IF EXISTS gin_index_skill_stud;
DROP INDEX IF EXISTS gin_index_subj;

DROP INDEX IF EXISTS gist_index_name_stud;
DROP INDEX IF EXISTS gist_index_surname_stud;
DROP INDEX IF EXISTS gist_index_skill_stud;
DROP INDEX IF EXISTS gist_index_subj;


ANALYZE VERBOSE students;
EXPLAIN (FORMAT JSON) SELECT * FROM students WHERE name = 'Albert';
SELECT relpages, reltuples FROM pg_class WHERE relname = 'students';

EXPLAIN (FORMAT JSON) SELECT * FROM students WHERE surname SIMILAR TO '%nely%';
SELECT relpages, reltuples FROM pg_class WHERE relname = 'students';

EXPLAIN (FORMAT JSON) SELECT * FROM students WHERE phone_numbers SIMILAR TO '%8010%';
SELECT relpages, reltuples FROM pg_class WHERE relname = 'students';

EXPLAIN (FORMAT JSON) SELECT students.name, students.surname, subjects.name, results.mark
FROM students LEFT JOIN results ON results.student_id = students.id
LEFT JOIN subjects ON results.subject_id = subjects.id
WHERE students.surname SIMILAR TO '%nely%';
SELECT relpages, reltuples FROM pg_class WHERE relname = 'students';
SELECT relpages, reltuples FROM pg_class WHERE relname = 'results';
SELECT relpages, reltuples FROM pg_class WHERE relname = 'subjects';

DELETE FROM students;

INSERT INTO students (name, surname, date_of_birth, phone_numbers, primary_skill) VALUES ('Ivan', 'Ivanov', '2000-10-01', '+380950950950', 'skill_one'), ('Petr', 'Petrov', '2000-10-01', '+380950950950', 'skill_two');

SELECT * FROM students WHERE name='Arkell';
SELECT * FROM students WHERE id>99997;


DROP TABLE results;
DROP TABLE subjects;
DROP TABLE students;