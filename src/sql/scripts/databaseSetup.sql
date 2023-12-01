DROP TABLE Employees CASCADE CONSTRAINTS ;
DROP TABLE Event CASCADE CONSTRAINTS ;
DROP TABLE Curator CASCADE CONSTRAINTS ;
DROP TABLE EventStaffSupervises CASCADE CONSTRAINTS ;
DROP TABLE Researcher CASCADE CONSTRAINTS ;
DROP TABLE Project CASCADE CONSTRAINTS ;
DROP TABLE worksOn CASCADE CONSTRAINTS ;
DROP TABLE Donor CASCADE CONSTRAINTS ;
DROP TABLE Funds CASCADE CONSTRAINTS ;
DROP TABLE Artist CASCADE CONSTRAINTS ;
DROP TABLE Artwork CASCADE CONSTRAINTS ;
DROP TABLE Exhibition CASCADE CONSTRAINTS ;
DROP TABLE Visitor CASCADE CONSTRAINTS ;
DROP TABLE ParticipateIn CASCADE CONSTRAINTS ;
DROP TABLE Host CASCADE CONSTRAINTS ;
DROP TABLE Visit CASCADE CONSTRAINTS ;

CREATE TABLE Employees
(employeeID INTEGER PRIMARY KEY,
 phoneNum VARCHAR(50),
 name VARCHAR(50),
 UNIQUE (phoneNum, name));

CREATE TABLE Event (
                       eventID INTEGER PRIMARY KEY,
                       ticketsSold INTEGER,
                       location VARCHAR(50),
                       eventDate VARCHAR(50),
                       capacity INTEGER,
                       title VARCHAR(50),
                       employeeID INTEGER,
                       UNIQUE (title, eventDate, location),
                       FOREIGN KEY (employeeID) REFERENCES Employees(employeeID)
);

CREATE TABLE Curator
(employeeID INTEGER PRIMARY KEY,
 specialization VARCHAR(50),
 FOREIGN KEY (employeeID) REFERENCES Employees(employeeID)ON DELETE CASCADE);

CREATE TABLE EventStaffSupervises
(employeeID INTEGER PRIMARY KEY,
 department VARCHAR(50),
 eventID INTEGER NOT NULL,
 FOREIGN KEY (employeeID) REFERENCES Employees,
 FOREIGN KEY (eventID) REFERENCES Event(eventID)ON DELETE CASCADE);

CREATE TABLE Researcher
(employeeID INTEGER PRIMARY KEY,
 researchInterest VARCHAR(50),
 FOREIGN KEY (employeeID) REFERENCES Employees(employeeID));

CREATE TABLE Project
(projectID INTEGER PRIMARY KEY,
 title VARCHAR(50),
 budget INTEGER,
 status VARCHAR(50),
 startDate VARCHAR(50),
 endDate VARCHAR(50));

CREATE TABLE worksOn
(employeeID INTEGER,
 projectID INTEGER,
 PRIMARY KEY (employeeID, projectID),
 FOREIGN KEY (employeeID) REFERENCES Employees(employeeID),
 FOREIGN KEY (ProjectID) REFERENCES Project(projectID));

CREATE TABLE Donor
(donorID INTEGER PRIMARY KEY,
 name VARCHAR(50),
 totalDonationValue INTEGER,
 phoneNum VARCHAR(50),
 country VARCHAR(50));


CREATE TABLE Funds
(donorID INTEGER,
 projectID INTEGER,
 amountGiven DECIMAL,
 PRIMARY KEY (donorID, projectID),
 FOREIGN KEY (donorID) REFERENCES Donor(donorID),
 FOREIGN KEY (ProjectID) REFERENCES Project(projectID));

CREATE TABLE Artist
( artistID INTEGER,
  name VARCHAR(50) NOT NULL,
  dateOfBirth VARCHAR(50) NOT NULL,
  dateOfDeath VARCHAR(50),
  skillLevel INTEGER,
  PRIMARY KEY (artistID),
  UNIQUE (name, dateOfBirth));

CREATE TABLE Exhibition
(exhibitionID INTEGER,
 title VARCHAR(50),
 startDate VARCHAR(50),
 endDate VARCHAR(50),
 visitorCount INTEGER,
 location VARCHAR(50),
 curatorID INTEGER,
 rating INTEGER,
 PRIMARY KEY (exhibitionID),
 FOREIGN KEY (curatorID) REFERENCES Curator(employeeID),
 UNIQUE (startDate, location));

CREATE TABLE Artwork
(artworkID INTEGER,
 artistID INTEGER,
 title VARCHAR(50) NOT NULL,
 dimensions VARCHAR(50),
 dateCreated VARCHAR(50) NOT NULL,
 displayMedium VARCHAR(50) NOT NULL,
 donorID INTEGER,
 featureID INTEGER,
 value INTEGER,
 PRIMARY KEY (artworkID, artistID),
 FOREIGN KEY (artistID) REFERENCES Artist(artistID) ON DELETE CASCADE,
 FOREIGN KEY (donorID) REFERENCES Donor(donorID) ON DELETE SET NULL,
 FOREIGN KEY (featureID) REFERENCES Exhibition(exhibitionID) ON DELETE SET NULL,
 UNIQUE (dateCreated, title, artistID));


CREATE TABLE Visitor (
                         visitorID INTEGER PRIMARY KEY,
                         name VARCHAR(50),
                         phoneNum VARCHAR(50),
                         UNIQUE (name, phoneNum)
);


CREATE TABLE ParticipateIn (
                               eventID INTEGER,
                               visitorID INTEGER,
                               PRIMARY KEY (eventID, visitorID),
                               FOREIGN KEY (eventID) REFERENCES Event(eventID) ON DELETE CASCADE,
                               FOREIGN KEY (visitorID) REFERENCES Visitor(visitorID)
);

CREATE TABLE Host (
                      eventID INTEGER,
                      artistID INTEGER,
                      PRIMARY KEY (eventID, artistID),
                      FOREIGN KEY (eventID) REFERENCES Event(eventID) ON DELETE CASCADE,
                      FOREIGN KEY (artistID) REFERENCES Artist(artistID) ON DELETE CASCADE
);

CREATE TABLE Visit (
                       visitorID INTEGER,
                       exhibitionID INTEGER,
                       PRIMARY KEY (visitorID, exhibitionID),
                       FOREIGN KEY (visitorID) REFERENCES Visitor(visitorID),
                       FOREIGN KEY (exhibitionID) REFERENCES Exhibition(exhibitionID)
);

INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2000, '111-222-333', 'John Smith');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2001, '111-222-334', 'Daniel Lee');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2002, '111-222-335', 'Mary Jane');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2003, '111-222-336', 'Jordan Johnson');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2004, '111-222-337', 'Sarah Jones');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2005, '111-222-338', 'Michael Kim');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2006, '111-222-339', 'Bianca Ng');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2007, '111-222-340', 'Emma Watson');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2008, '111-222-341', 'Emma Stone');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2009, '111-222-342', 'Margot Robbie');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2010, '111-222-343', 'Chris Hemsworth');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2011, '111-222-344', 'Chris Pratt');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2012, '111-222-345', 'Chris Pine');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2013, '111-222-346', 'Chris Brown');
INSERT INTO Employees (employeeID, phoneNum, name)
VALUES (2014, '111-222-347', 'Chris Paul');

INSERT INTO Curator (employeeID, specialization)
VALUES(2000, 'Contemporary');
INSERT INTO Curator (employeeID, specialization)
VALUES (2001, 'Modernism');
INSERT INTO Curator (employeeID, specialization)
VALUES (2002, 'Post Impressionism');
INSERT INTO Curator (employeeID, specialization)
VALUES (2003, 'Naturalism');
INSERT INTO Curator (employeeID, specialization)
VALUES (2004, 'Renaissance');

INSERT INTO Event (eventID, ticketsSold, location, eventDate, capacity, title, employeeID)
VALUES (101, 100, 'Venue A', '15/05/23', 150, 'Art Show A', 2001);
INSERT INTO Event (eventID, ticketsSold, location, eventDate, capacity, title, employeeID)
VALUES (102, 200, 'Venue B', '20/06/23', 250, 'Concert B', 2002);
INSERT INTO Event (eventID, ticketsSold, location, eventDate, capacity, title, employeeID)
VALUES (103, 50, 'Venue C', '10/07/23', 100, 'Exhibition C', 2003);

INSERT INTO EventStaffSupervises (employeeID, department, eventID)
VALUES
    (2005, 'Education', 101);
INSERT INTO EventStaffSupervises (employeeID, department, eventID)
VALUES (2006, 'Marketing', 101);
INSERT INTO EventStaffSupervises (employeeID, department, eventID)
VALUES (2007, 'Marketing', 102);
INSERT INTO EventStaffSupervises (employeeID, department, eventID)
VALUES (2008, 'Finance', 102);
INSERT INTO EventStaffSupervises (employeeID, department, eventID)
VALUES (2009, 'Communications', 103);

INSERT INTO Researcher (employeeID, researchInterest)
VALUES (2010, 'Art History');
INSERT INTO Researcher (employeeID, researchInterest)
VALUES (2011, 'Collection Studies');
INSERT INTO Researcher (employeeID, researchInterest)
VALUES (2012, 'Cultural Context');
INSERT INTO Researcher (employeeID, researchInterest)
VALUES (2013, 'Market and Value');
INSERT INTO Researcher (employeeID, researchInterest)
VALUES (2014, 'Conservation');

INSERT INTO Project (projectID, title, budget, status, startDate, endDate)
VALUES (1000, 'Women artists in 19C', 100, 'ongoing', '2021-12-23', '2024-11-11');
INSERT INTO Project (projectID, title, budget, status, startDate, endDate)
VALUES (1001, 'Asian Contemporary Art', 200, 'completed', '2020-02-01', '2022-04-03');
INSERT INTO Project (projectID, title, budget, status, startDate, endDate)
VALUES (1002, 'Evolution of Printmaking Techniques', 300, 'not started', '2024-06-20', '2026-11-03');
INSERT INTO Project (projectID, title, budget, status, startDate, endDate)
VALUES (1003, 'Digital Evolution in Conservation', 300, 'completed', '2022-05-04', '2023-02-02');
INSERT INTO Project (projectID, title, budget, status, startDate, endDate)
VALUES (1004, 'Cultural Heritage Preservation', 400, 'not started', '2026-01-01', '2029-04-04');

INSERT INTO worksOn (employeeID, projectID)
VALUES (2010, 1000);
INSERT INTO worksOn (employeeID, projectID)
VALUES (2011, 1001);
INSERT INTO worksOn (employeeID, projectID)
VALUES (2012, 1002);
INSERT INTO worksOn (employeeID, projectID)
VALUES (2013, 1003);
INSERT INTO worksOn (employeeID, projectID)
VALUES (2014, 1004);

INSERT INTO Donor (donorID, name, totalDonationValue, phoneNum, country)
VALUES (11, 'John Doe', 500, '123-456-7890', 'Canada');
INSERT INTO Donor (donorID, name, totalDonationValue, phoneNum, country)
VALUES (12, 'Jane Smith', 750, '987-654-3210', 'Canada');
INSERT INTO Donor (donorID, name, totalDonationValue, phoneNum, country)
VALUES (13, 'Alice Johnson', 300, '555-123-4567', 'USA');
INSERT INTO Donor (donorID, name, totalDonationValue, phoneNum, country)
VALUES (14, 'Bob Williams', 1000, '111-222-3333', 'Canada');
INSERT INTO Donor (donorID, name, totalDonationValue, phoneNum, country)
VALUES (15, 'Eva Brown', 200, '777-888-9999', 'USA');
INSERT INTO Donor (donorID, TotalDonationValue, phoneNum, name, country)
VALUES (16, 64000000, '473-928-7782', 'Billy Bob', 'USA');
INSERT INTO Donor (donorID, TotalDonationValue, phoneNum, name, country)
VALUES (17, 109000000, '110-273-6271', 'Jim Jones', 'Mexico');
INSERT INTO Donor (donorID, TotalDonationValue, phoneNum, name, country)
VALUES (18, 1112000, '182-999-9085', 'Harry Dewit', 'Canada');
INSERT INTO Donor (donorID, TotalDonationValue, phoneNum, name, country)
VALUES (19, 810200000, '111-521-1829', 'Gustavo Fring', 'USA');
INSERT INTO Donor (donorID, TotalDonationValue, phoneNum, name, country)
VALUES (20, 7718000, '902-892-7782', 'Jonas Cruz', 'Mexico');

INSERT INTO Visitor (visitorID, name, phoneNum) VALUES (1, 'John Smith', '123-456-7890');
INSERT INTO Visitor (visitorID, name, phoneNum) VALUES (2, 'Alice Johnson', '987-654-3210');
INSERT INTO Visitor (visitorID, name, phoneNum) VALUES (3, 'Bob Davis', '555-111-2222');

INSERT INTO Funds (donorID, projectID, amountGiven) VALUES (11, 1000, 500.00);
INSERT INTO Funds (donorID, projectID, amountGiven) VALUES (12, 1001, 750.00);
INSERT INTO Funds (donorID, projectID, amountGiven) VALUES (13, 1002, 300.00);
INSERT INTO Funds (donorID, projectID, amountGiven) VALUES (14, 1003, 1000.00);
INSERT INTO Funds (donorID, projectID, amountGiven) VALUES (15, 1004, 200.00);


INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES 	(51, 'Andy Warhol', 'August 6, 1928', 'February 22, 1987', 76);
INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (52, 'Leonardo Da Vinci', 'April 15, 1452', 'May 2, 1519', 96);
INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (53, 'Pablo Picasso', 'October 25, 1881', 'April 8, 1973', 86);
INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (54, 'Vincent van Gogh', 'March 30, 1853', 'July 29, 1890', 92);
INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (55, 'Gerhard Richter', 'February 9, 1932', NULL, 68);
INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (56, 'Claude Monet', 'November 14, 1840', 'December 5, 1926', 88);

INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (57, 'Georgia O''Keeffe', 'November 15, 1887', 'March 6, 1986', 84);

INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (58, 'Salvador Dalí', 'May 11, 1904', 'January 23, 1989', 90);

INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (59, 'Edvard Munch', 'December 12, 1863', 'January 23, 1944', 89);

INSERT INTO Artist(artistID, name, DateOfBirth, DateOfDeath, skillLevel)
VALUES (60, 'Frida Kahlo', 'July 6, 1907', 'July 13, 1954', 86);

INSERT INTO Exhibition(exhibitionID, title, startDate, endDate, visitorCount, location, curatorID, rating)
VALUES	(301, 'Emerging Echoes: New Artists Unveiled', '25/10/22', '9/8/23', 966178, 'room 15', 2000, 67);
INSERT INTO Exhibition(exhibitionID, title, startDate, endDate, visitorCount, location, curatorID, rating)
VALUES	(302, 'Urban Odyssey: Cityscape Creations', '15/7/15', '18/8/17', 67551092, 'Bianca Ballroom', 2004, 89);
INSERT INTO Exhibition(exhibitionID, title, startDate, endDate, visitorCount, location, curatorID, rating)
VALUES	(303, '3D Art Extravaganza', '18/2/18', '19/5/20', 11000000, 'Ferguson Garden', 2003, 99);
INSERT INTO Exhibition(exhibitionID, title, startDate, endDate, visitorCount, location, curatorID, rating)
VALUES	(304, 'Echoes of the Past: Historical Art Revival', '19/4/20', '20/7/25', 73827361, 'room 209', 2002, 78);
INSERT INTO Exhibition(exhibitionID, title, startDate, endDate, visitorCount, location, curatorID, rating)
VALUES	(305, 'Art for Environmental Change', '23/12/9', '15/6/17', 161657192, 'room 112', 2001, 92);

INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES	(91, 51, 'Green Coca-Cola Bottles', '82.8 in by 57.1 in', '1962', 78000000, 14, 302, 'painting');
INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (92, 52, 'The Virgin and Child with Saint Anne', '51 in by 66.3 in', '1510', 5780000000, 13, 301, 'painting');
INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (93, 51, 'Big Campbells Soup Can 19c (Beef Noodle)', '8.3 x 4.3 in.', '1962', 88000000, 11, 303, 'painting');
INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES(94, 55, 'Two Candles (Zwei Kerzen)', '47 1/4 × 39 1/2 in.', '1982', 9000000, 12, 305, 'painting');
INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES(95, 54, 'The Potato Eaters', '82 x 114 cm', '1885', 54000000, 14, 304, 'painting');
INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (96, 52, 'Mona Lisa', '30 in by 20 in', '1503', 1000000000, 11, 301, 'painting');

INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (97, 54, 'Starry Night', '29 in by 36 in', '1889', 85000000, 13, 302, 'painting');

INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (98, 55, 'Portrait of Adele Bloch-Bauer I', '54 in by 54 in', '1907', 78000000, 14, 303, 'painting');

INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (99, 53, 'Les Demoiselles d''Avignon', '8 ft by 7 ft', '1907', 120000000, 12, 304, 'painting');

INSERT INTO Artwork(artworkID, artistID, title, dimensions, dateCreated, value, donorID, featureID, displayMedium)
VALUES (100, 54, 'The Persistence of Memory', '9.5 in by 13 in', '1931', 95000000, 15, 305, 'painting');


INSERT INTO ParticipateIn (eventID, visitorID) VALUES (101,1);
INSERT INTO ParticipateIn (eventID, visitorID) VALUES (101,2);
INSERT INTO ParticipateIn (eventID, visitorID) VALUES (101,3);
INSERT INTO ParticipateIn (eventID, visitorID) VALUES (102,3);
INSERT INTO ParticipateIn (eventID, visitorID) VALUES (103,1);

INSERT INTO Host (eventID, artistID) VALUES (101, 51);
INSERT INTO Host (eventID, artistID) VALUES (101, 52);
INSERT INTO Host (eventID, artistID) VALUES (101, 53);

INSERT INTO Visit (visitorID, exhibitionID) VALUES (1, 301);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (1, 302);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (1, 303);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (1, 304);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (1, 305);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (2, 301);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (2, 302);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (2, 303);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (2, 304);
INSERT INTO Visit (visitorID, exhibitionID) VALUES (2, 305);