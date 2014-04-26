# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tracking (
  id                        integer auto_increment not null,
  address                   varchar(255),
  constraint pk_tracking primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tracking;

SET FOREIGN_KEY_CHECKS=1;

