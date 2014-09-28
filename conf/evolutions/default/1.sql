# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tracking (
  id                        integer not null,
  address                   varchar(255),
  constraint pk_tracking primary key (id))
;

create sequence tracking_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists tracking;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists tracking_seq;

