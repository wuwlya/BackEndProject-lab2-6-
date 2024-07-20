CREATE TABLE members
(
    id bigserial,
    name VARCHAR(40),
    surname VARCHAR(40),
    profession VARCHAR (255),
    activity BOOLEAN,
    CONSTRAINT pr_id PRIMARY KEY (id)
)