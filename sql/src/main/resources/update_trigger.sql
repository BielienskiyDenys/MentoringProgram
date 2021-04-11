/* Trigger to update datetime on updating students table */

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_datetime = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER refresh_updated_timestamp
BEFORE UPDATE ON students
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

/* Additional commands to check result */
select * from students where id = 997;

UPDATE students
SET phone_numbers = '+380993734900'
WHERE id = 997;