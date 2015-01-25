INSERT INTO ryhma values(DEFAULT,'Juoksu','Juoksujaoston ilmoituksia ja lenkkikutsuja');
INSERT INTO ryhma values(DEFAULT,'Karate','Karatejaoston ilmoituksia ja kurssikutsuja');
INSERT INTO ryhma values(DEFAULT,'Kirpputori','Jäsenten urheiluvälineiden osto- ja myynti-ilmoituksia');
INSERT INTO ryhma values(DEFAULT,'Luistelu','Retkiluistelujaoston retkikutsuja ja kimppakyytejä');
INSERT INTO ryhma values(DEFAULT,'Soutu','Soutujaoston ilmoituksia');
INSERT INTO ryhma values(DEFAULT,'Suunnistus','Suunnistusjaoston ilmoituksia ja kuntorastitapahtumia');

INSERT INTO jasen values(DEFAULT,'Kalle','Aalto','kalle.aalto@hkl.fi','kops34rj','kayttaja');
INSERT INTO jasen values(DEFAULT,'Liisa','Dufva','liisa.dufva@abc.fi','luor4b6i','kayttaja');
INSERT INTO jasen values(DEFAULT,'Jaana','Suvanto','suvis@helsinki.fi','suvis8uvi8','yllapitaja');
INSERT INTO jasen values(DEFAULT,'Jan','Wiik','jan.Wiik@laituri.fi','ja54ftrr','kayttaja');

INSERT INTO viesti values(DEFAULT,'Myydään käytetyt luistimet','Myydään käytetyt miesten luistimet, hyväkuntoiset. Koko 42.',NULL,3,DEFAULT,1);
INSERT INTO viesti values(DEFAULT,'Myydään käytetyt luistimet','Mies tarttisi luistimet, missä niitä voisi sovittaa?',1,3,DEFAULT,2);
INSERT INTO viesti values(DEFAULT,'Myydään käytetyt luistimet','Ne ovat täällä Martinlaaksossa, voin tuoda ne esim. keskustaan. Soittele, p. 3333 33345',1,3,DEFAULT,1);
INSERT INTO viesti values(DEFAULT,'Juoksulenkki peruttu ti','Tiistain yhteislenkki peruttu kovan pakkasen takia. Yritetään ensi viikolla uudelleen!',NULL,1,DEFAULT,3);


INSERT INTO ryhmanjasenet values(1,1);
INSERT INTO ryhmanjasenet values(1,3);
INSERT INTO ryhmanjasenet values(1,4);
INSERT INTO ryhmanjasenet values(2,1);
INSERT INTO ryhmanjasenet values(3,1);
INSERT INTO ryhmanjasenet values(3,2);
INSERT INTO ryhmanjasenet values(3,3);
INSERT INTO ryhmanjasenet values(5,4);
INSERT INTO ryhmanjasenet values(6,2);


INSERT INTO jasenenlukematviestit values(1,1);
INSERT INTO jasenenlukematviestit values(1,2);
INSERT INTO jasenenlukematviestit values(1,3);
INSERT INTO jasenenlukematviestit values(2,1);
INSERT INTO jasenenlukematviestit values(4,1);


