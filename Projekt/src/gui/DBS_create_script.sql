-- Löschen aller möglich existierenden Tabellen und Views
DROP VIEW IF EXISTS saalbelegung;
DROP VIEW IF EXISTS aktuellevorstellungen;
DROP TABLE IF EXISTS Vorstellung_Preisaufschlag;
DROP TABLE IF EXISTS Ort_PLZ;
DROP TABLE IF EXISTS Film_Hauptdarsteller;
DROP TABLE IF EXISTS Platz_Reservierung;
DROP TABLE IF EXISTS Vorstellung_Film;
DROP TABLE IF EXISTS Platz;
DROP TABLE IF EXISTS Reservierung;
DROP TABLE IF EXISTS Vorstellung;
DROP TABLE IF EXISTS Film;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Kunde;
DROP TABLE IF EXISTS Hauptdarsteller;
DROP TABLE IF EXISTS Preisaufschlag;
DROP TABLE IF EXISTS Sitzplatzkategorie;
DROP TABLE IF EXISTS Saal;


-- Erstellen der Datenbanken
CREATE TABLE Saal(
    bezeichnung VARCHAR(255) PRIMARY KEY);

CREATE TABLE Sitzplatzkategorie(
    bezeichnung VARCHAR(255) PRIMARY KEY,
    preis NUMERIC(5,2));
    
CREATE TABLE Preisaufschlag(
    bezeichnung VARCHAR(255) PRIMARY KEY,
    preis NUMERIC(5,2));
    
CREATE TABLE Hauptdarsteller(
	name VARCHAR(255),
	vorname VARCHAR(255),
	PRIMARY KEY (name,vorname));

CREATE TABLE Kunde(
    email VARCHAR(255) PRIMARY KEY CHECK(email LIKE '%@%.%'),
    name VARCHAR(255) NOT NULL,
    vorname VARCHAR(255) NOT NULL,
    geburtsdatum DATE NOT NULL,
    passwort VARCHAR(255) NOT NULL,
    plz INT,
    strasse VARCHAR(255),
    handy VARCHAR(255),
    festnetz VARCHAR(255));
    
CREATE TABLE Genre(
	name VARCHAR(255) PRIMARY KEY);
       
CREATE TABLE Film(
    id INT PRIMARY KEY,
    bewertung INT,
    titel VARCHAR(255),
    fsk INT,
    genre_name VARCHAR(255) REFERENCES Genre(name));
    
CREATE TABLE Vorstellung(
	id SERIAL PRIMARY KEY, 
    zeit TIMESTAMP,
    saal_bezeichnung VARCHAR(255) REFERENCES Saal(bezeichnung));

CREATE TABLE Reservierung(
    id SERIAL PRIMARY KEY,
    kunde_email VARCHAR(255) REFERENCES Kunde(email),
	vorstellung_id INT REFERENCES Vorstellung(id));  
    
CREATE TABLE Platz(
    reihe INT,
    nummer INT,
    saal_bezeichnung VARCHAR(255) REFERENCES Saal(bezeichnung),
    kategorie_bezeichnung VARCHAR(255) REFERENCES Sitzplatzkategorie(bezeichnung),
    PRIMARY KEY(reihe,nummer,saal_bezeichnung));
    
CREATE TABLE Vorstellung_Film(
	vorstellung_id INT REFERENCES Vorstellung(id),
	film_id INT REFERENCES Film(id),
	PRIMARY KEY (vorstellung_id));
	
CREATE TABLE Platz_Reservierung(
	reservierung_id INT ,
	platz_reihe INT,
	platz_nummer INT,
	platz_saal_bezeichnung VARCHAR(255) REFERENCES Saal(bezeichnung),
	FOREIGN KEY(platz_reihe,platz_nummer,platz_saal_bezeichnung) REFERENCES Platz(reihe,nummer,saal_bezeichnung),
	PRIMARY KEY (reservierung_id,platz_reihe,platz_nummer,platz_saal_bezeichnung));
 
CREATE TABLE Film_Hauptdarsteller(
	film_id INT REFERENCES Film(id),
	hauptdarsteller_name VARCHAR(255),
	hauptdarsteller_vorname VARCHAR(255),
	FOREIGN KEY (hauptdarsteller_name,hauptdarsteller_vorname) REFERENCES Hauptdarsteller(name,vorname),
	PRIMARY KEY (film_id,hauptdarsteller_name,hauptdarsteller_vorname));

CREATE TABLE Ort_PLZ(
	plz INT PRIMARY KEY,
	ort VARCHAR(255));  

    
CREATE TABLE Vorstellung_Preisaufschlag(
    preisaufschlag_name VARCHAR(255) REFERENCES Preisaufschlag(bezeichnung),
    vorstellung_id INT REFERENCES Vorstellung(id),
    PRIMARY KEY (preisaufschlag_name,vorstellung_id));
    
-- Erstellen der Views


/*
	Dieser View stellt Daten zur aktuellen Saalbelegung/Vorstellungsbuchung bereit
	Zu jeder Vorstellung wird angegeben, wie viele Plaetze belegt sind, wie viele es insgesamt gibt sowie
	die Bezeichnung des Saales und die Vorstellungszeit
	Es wird nach Saalbezeichnung sortiert
*/
CREATE VIEW saalbelegung AS
SELECT b.belegt,b.zeit,b.saal_bezeichnung, c.gesamt
FROM (SELECT belegt,vorstellung_id,zeit,saal_bezeichnung FROM (SELECT count(r.*) AS belegt, vorstellung_id
FROM reservierung r group by vorstellung_id) a
join vorstellung v
on a.vorstellung_id = v.id) b
join
(SELECT count(*) as gesamt, saal_bezeichnung
FROM platz GROUP BY saal_bezeichnung) c
on b.saal_bezeichnung = c.saal_bezeichnung
WHERE zeit>=now()
ORDER BY saal_bezeichnung;

/*
	Der View stellt Daten zu aktuellen und zukuenftigen Vorstellungen bereit.
	Es werden Vorstellungsid, Vorstellungszeit, die Bezeichnungs des Saales und der Titel des Filmes ausgegeben
*/
CREATE VIEW aktuellevorstellungen AS
select v.id, v.zeit, v.saal_bezeichnung, f.titel
from vorstellung v
join vorstellung_film vf
on v.id = vf.vorstellung_id
join film f
on vf.film_id = f.id
WHERE zeit>=now()
ORDER by v.zeit;


-- Fuellen mit Beispieldaten -- 
	-- Einspeisen der Säle  
INSERT INTO Saal VALUES 
('Saal A'), 
('Saal B'), 
('Grosser Saal'), 
('Kleiner Saal');
	-- Einspeisen der Sitzplatzkategorien vorne / mitte / hinten
INSERT INTO Sitzplatzkategorie VALUES 
('vorne', 1.50), 
('mitte', 2.50), 
('hinten', 3.50);
	-- Einspeisen der Preisaufschlagskategorien 3d / Ueberlaenge / Premiere
INSERT INTO Preisaufschlag VALUES 
('3D', 0.50), 
('Ueberlaenge', 1.50), 
('Premiere', 5.00);
	-- Einspeisen diverser Hauptdarsteller von Filmen
INSERT INTO Hauptdarsteller VALUES
('Willes','Bruce'),
('Smith', 'Will'),
('Johannsen', 'Scarlett'),
('Depp','Jonny'),
('Steward','Christen'),
('Pitt','Brett'),
('Joly','Angelina');
	-- Einspeisen zweier Testkunden mit Ausgedachten Daten
INSERT INTO Kunde VALUES
('hans@peter.de', 'Peter', 'Hans', '1981-05-19', 'Eyjafjallajoekull',314159, 'Strassenstrasse 1', '1618/6774217', '32712/1398315' ),
('test@kind.de', 'mit Nachname', 'Testkind', '2001-12-24','MadHaXX0rSk1lLz',  123456, 'keine ahnung', '1234', '1234');
	-- Einspeisen von allen ofizellen Filmgenre
INSERT INTO Genre VALUES
('Filmkomödie'),
('Western'),
('Eastern'),
('Melodram'),
('Musical'),
('Kriegsfilm'),
('Erotikfilm'),
('Piratenfilm'),
('Roadmovie'),
('Science-Fiction-Film'),
('Fantasyfilm'),
('Horrorfilm'),
('Actionfilm'),
('Thriller'),
('Drama');

	-- Einspeisen von Filmen mit der Struktur ID | Bewertung | Titel | FSK | Genre
INSERT INTO Film VALUES 
(0, 6 ,'Saw', 18,'Thriller'), 
(1, 7, 'Fluch der Karibik',12, 'Piratenfilm'),
(2, 8,'The Dark Knight',16,'Actionfilm'),
(3, 7,'Eine Taube sitzt auf einem Zweig und denkt über das Leben nach ',12,'Filmkomödie');

	-- Einspeisen von Testvorstellungen zu einer Bestimmten Zeit in einem Saal
INSERT INTO Vorstellung(zeit,saal_bezeichnung) VALUES
('2015-03-15 13:00:00','Saal A'),
('2015-03-15 13:00:00','Saal B'),
('2015-03-15 14:00:00','Grosser Saal'),
('2015-03-15 14:00:00','Kleiner Saal');

	-- Einspeisen von Testreservierungen mit den beiden Vorhandenen Kunden
INSERT INTO Reservierung VALUES
(DEFAULT,'hans@peter.de',1),
(DEFAULT,'test@kind.de', 2);
	
	-- Eintragen von vorhanden Plaetzen in vorhandenen Sälen 
INSERT INTO Platz VALUES
(1, 1, 'Saal A', 'vorne'),
(1, 2, 'Saal A', 'vorne'),
(2, 1, 'Saal A', 'mitte'),
(2, 2, 'Saal A', 'mitte'),
(3, 1, 'Saal A', 'hinten'),
(3, 2, 'Saal A', 'hinten'),
(1, 1, 'Saal B', 'vorne'),
(1, 2, 'Saal B', 'vorne'),
(2, 1, 'Saal B', 'hinten'),
(2, 2, 'Saal B', 'hinten'),
(1, 1, 'Grosser Saal', 'vorne'),
(1, 2, 'Grosser Saal', 'vorne'),
(2, 1, 'Grosser Saal', 'mitte'),
(2, 2, 'Grosser Saal', 'mitte'),
(3, 1, 'Grosser Saal', 'hinten'),
(3, 2, 'Grosser Saal', 'hinten'),
(1, 1, 'Kleiner Saal', 'vorne'),
(1, 2, 'Kleiner Saal', 'vorne');

	-- Verknüpfen von Vorstellung 
INSERT INTO Vorstellung_Film VALUES
(1,0),
(2,1),
(3,2),
(4,2);
	-- Reservieren bestimmter Plaetze zu einer bestimmten Reservierung zu einer bestimmten Vorstellung in einem Saal
INSERT INTO Platz_Reservierung VALUES 
(1,1,2,'Saal A'),
(2,2,2,'Saal B');

	-- Fuegt Hauptdarsteller zum bestimmten Film hinzu 
INSERT INTO Film_Hauptdarsteller VALUES
(1,'Depp','Jonny');
	-- Fuegt zur PLZ den Ort hinzu bzw holt ihn aus der Datenbank falls vorhanden sodass keine Anomalien estehen
INSERT INTO Ort_PLZ VALUES 
(314159,'Entenhausen'),
(123456,'Disneyland');
	-- Hinzufuegen von Preisaufschlaegen zu einer gewissen Vorstellung
INSERT INTO Vorstellung_Preisaufschlag VALUES
('3D', 1);




    