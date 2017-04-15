CREATE TABLE post (
  id VARCHAR (255) NOT NULL,
  create_date TIMESTAMP NOT NULL,
  text VARCHAR (4000) NOT NULL,
  sender_id VARCHAR (255) NOT NULL,
  receiver_id VARCHAR (255) NOT NULL
);

ALTER TABLE post ADD PRIMARY KEY (id);