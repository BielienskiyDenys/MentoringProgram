COPY (
	SELECT
	id AS "_id",
	title,
	date,
	ticket_price AS "ticketPrice",
	'com.epam.mentoring.model.Event' AS "_calss"
	FROM events
) TO '/events.csv' WITH (FORMAT CSV, HEADER TRUE, DELIMITER E'\t');

COPY (
	SELECT
	id AS "_id",
	email,
	name,
	'com.epam.mentoring.model.User' AS "_calss"
	FROM users
) TO '/users.csv' WITH (FORMAT CSV, HEADER TRUE, DELIMITER E'\t');

COPY (
	SELECT
	id AS "_id",
	event_id AS "eventId",
	user_id AS "userId",
	category,
	place,
	'com.epam.mentoring.model.Ticket' AS "_calss"
	FROM tickets
) TO '/tickets.csv' WITH (FORMAT CSV, HEADER TRUE, DELIMITER E'\t');

COPY (
	SELECT
	accounts.user_id AS "_id",
	users.id AS "user._id",
	users.name AS "user.name",
	users.email AS "user.email",
	accounts.balance AS "balance",
	'com.epam.mentoring.model.UserAccount' AS "_calss"
	FROM accounts LEFT JOIN users ON accounts.user_id = users.id
) TO '/accounts.csv' WITH (FORMAT CSV, HEADER TRUE, DELIMITER E'\t');