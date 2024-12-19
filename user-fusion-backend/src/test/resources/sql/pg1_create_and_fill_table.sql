
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
