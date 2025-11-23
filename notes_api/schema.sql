CREATE TABLE note (id integer, author varchar(255), content clob, created varchar(255), modified varchar(255), priority integer not null, status varchar(255) check (status in ('REVIEW','COMPLETE')), title varchar(255), primary key (id));
CREATE TABLE note_tags (note_id bigint not null, tags varchar(255));
