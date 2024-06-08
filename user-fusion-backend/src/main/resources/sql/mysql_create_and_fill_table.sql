
DROP TABLE IF EXISTS `user_table`;


CREATE TABLE `user_table` (

  `oauth_login` varchar(100) default NULL,

  `us_name` varchar(255) default NULL,

  `fname` varchar(255) default NULL,

  `sname` varchar(255) default NULL

);


INSERT INTO `user_table` (`oauth_login`,`us_name`,`fname`,`sname`)

VALUES

  ("07106","pharetra.sed@outlook.ca","Leslie","Marks"),

  ("31238","arcu.eu@outlook.couk","Elaine","Sherman"),

  ("14465-595","vivamus.nisi@icloud.couk","Ina","Richard"),

  ("352244","amet.ultricies@outlook.net","Alana","Riddle"),

  ("0828","congue.turpis@google.edu","Freya","Santana"),

  ("844398","inceptos.hymenaeos.mauris@hotmail.couk","Mufutau","Slater"),

  ("6740-9527","gravida.aliquam.tincidunt@google.net","Jacob","Becker"),

  ("86-34","dictum@icloud.edu","Philip","Shepard"),

  ("785228","eu@yahoo.org","Arthur","Fox"),

  ("1351","ipsum.suspendisse@protonmail.org","Erich","Wilder");

INSERT INTO `user_table` (`oauth_login`,`us_name`,`fname`,`sname`)

VALUES

  ("435018","fermentum@hotmail.edu","Lila","Wiggins"),

  ("63128","vivamus.nisi.mauris@google.ca","Blaze","Lara"),

  ("383483","sem.egestas@yahoo.edu","Sigourney","Harrell"),

  ("821558","donec.est.mauris@outlook.net","Owen","Mcdonald"),

  ("35726771","scelerisque.neque.sed@outlook.org","Alvin","Klein"),

  ("1753","a.odio@aol.ca","Hayes","Rivera"),

  ("5737-1305","sed.sem.egestas@protonmail.net","Kennedy","Garcia"),

  ("21708","sit.amet@hotmail.ca","Keefe","Thornton"),

  ("97-54","suspendisse.sagittis@yahoo.edu","Tucker","Nunez"),

  ("3657 CT","nunc.nulla.vulputate@yahoo.couk","Desirae","Anthony");
