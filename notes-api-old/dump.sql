PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE note (id bigint integer, author varchar(255), content clob, created timestamp(6), modified timestamp(6), priority integer not null, status varchar(255) check (status in ('REVIEW','COMPLETE')), title varchar(255), primary key (id));
CREATE TABLE note_tags (note_id bigint not null, tags varchar(255));
COMMIT;
