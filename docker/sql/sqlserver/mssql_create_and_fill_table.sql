IF EXISTS(SELECT 1 FROM sys.tables WHERE object_id = OBJECT_ID('users_table'))
BEGIN;
    DROP TABLE [users_table];
END;

GO


CREATE TABLE [users_table] (

    [id] INTEGER NOT NULL IDENTITY(1, 1),

    [postalZip] VARCHAR(10) NULL,

    [usr_name] VARCHAR(255) NULL,

    [first_name] VARCHAR(255) NULL,

    [surname] VARCHAR(255) NULL,

    PRIMARY KEY ([id])

);

GO


INSERT INTO [users_table] (postalZip,usr_name,first_name,surname)

VALUES

  ('37-23','lacus.pede@hotmail.net','Carla','Frazier'),

  ('79342','urna.nec@yahoo.couk','Burke','Hoffman'),

  ('690458','nunc.nulla.vulputate@aol.edu','Harper','Peck'),

  ('6951','nascetur.ridiculus@google.ca','Quyn','Duncan'),

  ('5604','cras.dolor.dolor@aol.couk','Jasper','Bonner'),

  ('8422','habitant.morbi@aol.net','Germaine','Price'),

  ('193272','scelerisque@yahoo.couk','Hilary','Cochran'),

  ('18898','sollicitudin@hotmail.edu','Holmes','Lyons'),

  ('284722','vestibulum.mauris@google.com','Bert','Cross'),

  ('276145','vitae.velit@yahoo.net','Audra','Merritt');

INSERT INTO [users_table] (postalZip,usr_name,first_name,surname)

VALUES

  ('882433','eu.dui@hotmail.com','Sawyer','Garcia'),

  ('9706','sapien.cursus.in@outlook.couk','Baxter','Powers'),

  ('55-22','ridiculus@yahoo.org','Fritz','Robbins'),

  ('3389','sed.dictum@outlook.edu','Liberty','Schmidt'),

  ('877837','aliquet.magna@outlook.net','Amena','Cherry'),

  ('8751','vestibulum.nec@aol.com','Forrest','Espinoza'),

  ('S8Y 2B3','semper.nam.tempor@aol.org','Keelie','Strickland'),

  ('18-178','consectetuer.rhoncus@protonmail.com','Patience','Shepard'),

  ('5636','est.vitae.sodales@aol.net','Tara','Barnett'),

  ('1716','nulla.semper.tellus@outlook.net','Naomi','Cooke');
