
DROP TABLE IF EXISTS "users";


CREATE TABLE "users" (

  user_id varchar(100) default NULL,

  login varchar(255) default NULL,

  first_name varchar(255) default NULL,

  last_name varchar(255) default NULL

);


INSERT INTO users (user_id,login,first_name,last_name)

VALUES

  ('72-888','nunc@icloud.edu','Phelan','Franco'),

  ('167528','leo.morbi.neque@icloud.ca','Zorita','Clay'),

  ('S1R 4B8','eu.enim@icloud.couk','Beatrice','Long'),

  ('58301-447','faucibus.id@icloud.org','Moses','Douglas'),

  ('23351','sodales.purus.in@aol.couk','Jordan','Norton'),

  ('T5C 2E8','pharetra.nibh@outlook.net','Robert','Roberts'),

  ('749876','nascetur.ridiculus@aol.net','Joel','Cervantes'),

  ('7145','eget.mollis.lectus@hotmail.net','Eliana','Turner'),

  ('255283','cursus.purus.nullam@yahoo.net','Hunter','Woods'),

  ('9304-5714','nec.metus@outlook.org','Alma','Garrett');

INSERT INTO users (user_id,login,first_name,last_name)

VALUES

  ('1163-4511','in@hotmail.net','Germaine','Alvarado'),

  ('M72 5UN','cras@outlook.net','Henry','Morton'),

  ('32309','molestie@yahoo.ca','Flynn','Rowe'),

  ('2314-1145','sociosqu@icloud.ca','Whoopi','Fox'),

  ('59504','eu.ligula@google.net','Ariel','Golden'),

  ('33187-28225','facilisis@google.net','Linda','Gross'),

  ('65855-773','vestibulum@yahoo.org','Moana','Schmidt'),

  ('62-14','et.tristique.pellentesque@icloud.edu','September','Bishop'),

  ('7165','arcu@hotmail.couk','Courtney','Crane'),

  ('8482-5386','auctor.mauris@outlook.couk','Wendy','Wallace');
  


DROP TABLE IF EXISTS "user_table";

CREATE TABLE "user_table" (
  ldap_login varchar(100) default NULL,

  email varchar(255) default NULL,

  name varchar(255) default NULL,

  surname varchar(255) default NULL

);

INSERT INTO user_table (ldap_login,email,name,surname)

VALUES

  ('3386','luctus.aliquet@hotmail.net','Orli','Holman'),

  ('1093 DD','vel.quam@yahoo.edu','Evan','Crawford'),

  ('380564','cursus.in@hotmail.net','Emerald','Reeves'),

  ('119487','vivamus.nisi@aol.edu','Tara','Farley'),

  ('65477','nec@aol.couk','Jordan','Hubbard'),

  ('R4J 7Y1','ornare.libero.at@google.com','Roth','Hutchinson'),

  ('98393','malesuada.vel.convallis@yahoo.net','Guy','Marks'),

  ('5652','malesuada.vel@icloud.com','Juliet','Stokes'),

  ('26881-61284','cum.sociis.natoque@protonmail.com','Teegan','Cote'),

  ('488042','eu.dolor.egestas@hotmail.org','Jamal','Mckinney');

INSERT INTO user_table (ldap_login,email,name,surname)

VALUES

  ('434575','sollicitudin.adipiscing.ligula@google.ca','Oprah','Hahn'),

  ('42888','sed.pede@protonmail.net','Imelda','Santana'),

  ('03225','proin.vel.nisl@protonmail.com','Lisandra','Cardenas'),

  ('9646','cras@yahoo.com','Orson','Jackson'),

  ('7141','velit.egestas.lacinia@protonmail.ca','Devin','Watson'),

  ('27235','malesuada.fames.ac@icloud.ca','John','Bright'),

  ('039047','neque@hotmail.org','Len','Sears'),

  ('228283','orci@icloud.com','Blaze','Carson'),

  ('585187','vulputate.lacus@outlook.ca','Abdul','Osborn'),

  ('29686','facilisi.sed@hotmail.net','Lyle','Rios');

