

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
