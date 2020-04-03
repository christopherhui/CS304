DROP TABLE Requirement1;
DROP TABLE Requirement3;
DROP TABLE Requirement4;
DROP TABLE Applicant1;
DROP TABLE Applicant3;
DROP TABLE Applicant4;
DROP TABLE Degree;
DROP TABLE Intern;
DROP TABLE Part_Time;
DROP TABLE Full_Time;
DROP TABLE Attended;
DROP TABLE Job;
DROP TABLE Platform;
DROP TABLE Applied_For;
DROP TABLE Application_Through_For;

CREATE TABLE Requirement1
    (Location		CHAR(20),
    Citizenship		CHAR (20),
    PRIMARY KEY 	(Location));

CREATE TABLE Requirement3
    (Skills		CHAR(100),
    GPA		REAL,
    PRIMARY KEY 	(Skills));

CREATE TABLE Requirement4
    (Location	CHAR(20),
    Skills		CHAR (20),
    Job_number	INTEGER 	NOT NULL,
    Company_Name CHAR(20),
    Experience	CHAR(30),
    PRIMARY KEY 	(Location, Skills),
    FOREIGN KEY (Job_Number, Company_Name)
    REFERENCES Job ON DELETE CASCADE,
    UNIQUE 	(Job_Number, Company_Name));

CREATE TABLE Applicant1
    (FirstName		CHAR(20),
    Address		    CHAR (30),
    DateOfBirth		DATE,
    PRIMARY KEY 	(FirstName, Address));

CREATE TABLE Applicant3
    (FirstName	CHAR(20),
    LastName	CHAR(20),
    Address	CHAR(30),
    PRIMARY KEY (FirstName, LastName));

CREATE TABLE Applicant4
    (SIN		INTEGER,
    Year		INTEGER NOT NULL,
    Major		INTEGER NOT NULL,
    FirstName	CHAR(20),
    Address	CHAR(30),
    PRIMARY KEY (SIN),
    FOREIGN KEY (Year, Major) REFERENCES Degree ON DELETE CASCADE);

CREATE TABLE Company
	(Company_Name 	CHAR(20),
    HiringAmt		INTEGER,
    Type			CHAR(20),
    PRIMARY KEY		(Company_Name));

CREATE TABLE Degree
    (Year		INTEGER,
    Major		CHAR(20),
    Faculty		CHAR(20),
    PRIMARY KEY (Year, Major));

CREATE TABLE Intern
    (SIN			INTEGER,
    Number_Of_Terms	INTEGER,
    PRIMARY KEY (SIN),
    FOREIGN KEY (SIN) REFERENCES Applicant4);

CREATE TABLE Part_Time
    (SIN			INTEGER,
    Hours_Per_Week	INTEGER,
    PRIMARY KEY (SIN),
    FOREIGN KEY (SIN) REFERENCES Applicant4);

CREATE TABLE Full_Time
    (SIN			INTEGER,
    Years_Worked		INTEGER,
    PRIMARY KEY (SIN),
    FOREIGN KEY (SIN) REFERENCES Applicant4);

CREATE TABLE School
    (School_Name	CHAR(20),
     Location	CHAR(20),
        PRIMARY KEY (School_Name));

CREATE TABLE Attended
    (SIN			INTEGER,
    School_Name		CHAR(20),
    PRIMARY KEY (SIN, School_Name),
    FOREIGN KEY (SIN) REFERENCES Applicant4,
    FOREIGN KEY (School_Name) REFERENCES School);

CREATE TABLE Job
	(Job_Number     INTEGER
    Company_Name 	CHAR(25) NOT NULL,
    Job_Title 		CHAR(15),
    Description 		CHAR(40),
    PRIMARY KEY (Job_Number, Company_Name),
    FOREIGN KEY (Company_Name) REFERENCES Company ON DELETE CASCADE);

CREATE TABLE Platform
	(Platform_Name	CHAR (15),
    Type CHAR (15),
    PRIMARY KEY (Platform_Name));

CREATE TABLE Applied_For
  (SIN            INTEGER,
  Company_Name    CHAR(25),
  App_ID          INTEGER,
  PRIMARY KEY (SIN, Company_Name, App_ID),
  FOREIGN KEY (SIN) REFERENCES Applicant4 ON DELETE CASCADE,
  FOREIGN KEY (Company_Name) REFERENCES Company ON DELETE CASCADE,
  FOREIGN KEY (App_ID) REFERENCES Application_Through_For ON DELETE CASCADE);

CREATE TABLE Application_Through_For
	(SIN			INTEGER,
    Company_Name	CHAR(25),
    App_ID			INTEGER,
    Platform_Name		CHAR(15) NOT NULL,
    Documents		CHAR(20),
    Status			CHAR(10),
    PRIMARY KEY (SIN, Company_Name, App_ID),
    FOREIGN KEY (SIN) REFERENCES Applicant4 ON DELETE CASCADE,
    FOREIGN KEY (Company_Name) REFERENCES Company ON DELETE CASCADE,
    FOREIGN KEY (Platform_Name) REFERENCES Platform ON DELETE CASCADE);

INSERT INTO Requirement1
VALUES ('Vancouver BC' , 'Canada');

INSERT INTO Requirement1
VALUES ('Toronto ON' , 'Canada');

INSERT INTO Requirement1
VALUES ('Seattle WA' , 'United States');

INSERT INTO Requirement1
VALUES ('Calgary AB' , 'Canada');

INSERT INTO Requirement1
VALUES ('Richmond BC' , 'Canada');

INSERT INTO Requirement3
VALUES ('Adobe Creative Suite, Sketch, HTML', 3.00);

INSERT INTO Requirement3
VALUES ('Accounting, Financial Modeling', 3.20);

INSERT INTO Requirement3
VALUES ('R, Statistics, Python', 3.50);

INSERT INTO Requirement3
VALUES ('Mailchimp, Wordpress, Social Media', 3.00);

INSERT INTO Requirement3
VALUES ('Excel, Word, Powerpoint, Outlook', 3.00);

INSERT INTO Requirement4
VALUES ('Vancouver BC', 'Adobe Creative Suite, Sketch, HTML', 1000, 'Best Buy', 2);

INSERT INTO Requirement4
VALUES ('Toronto ON', 'Accounting, Financial Modeling', 1001, 'EY', 3);

INSERT INTO Requirement4
VALUES ('Seattle WA', 'R, Statistics, Python', 1002, 'Amazon', 2);

INSERT INTO Requirement4
VALUES ('Calgary AB', 'Mailchimp, Wordpress, Social Media', 1003, 'ABC Marketing Agency', 1);

INSERT INTO Requirement4
VALUES ('Richmond BC', 'Excel, Word, Powerpoint, Outlook', 1004, 'UGO', 1);

INSERT INTO Applicant1
VALUES ('John', '3909 Harper Street', 1989-07-26);

INSERT INTO Applicant1
VALUES ('Christopher', '2359 Red Hawk Road', 1955-08-29);

INSERT INTO Applicant1
VALUES ('Nick', '3061 Park Street', 1978-01-10);

INSERT INTO Applicant1
VALUES ('Peter', '2403 Myra Street', 1991-05-23);

INSERT INTO Applicant1
VALUES ('Ivan', '492 Farm Meadow Drive', 1968-11-06);

INSERT INTO Applicant3
VALUES ('John', 'Smith', '3909 Harper Street');

INSERT INTO Applicant3
VALUES ('Christopher', 'Robins', '2359 Red Hawk Road');

INSERT INTO Applicant3
VALUES ('Nick', 'Fury', '3061 Park Street');

INSERT INTO Applicant3
VALUES ('Peter','Leibniz', '2403 Myra Street');

INSERT INTO Applicant3
VALUES ('Ivan', 'Terrible', '492 Farm Meadow Drive');

INSERT INTO Applicant4
VALUES (777777777, 3, 'Psychology', 'John','3909 Harper Street');

INSERT INTO Applicant4
VALUES (788888888, 3, 'Computer Science','Christopher','2359 Red Hawk Road');

INSERT INTO Applicant4
VALUES (977777777, 3, 'Business','Nick', '3061 Park Street');

INSERT INTO Applicant4
VALUES (999999999, 4, 'Economics','Peter', '2403 Myra Street');

INSERT INTO Applicant4
VALUES (799999999,5,'History' ,'Ivan','492 Farm Meadow Drive');

INSERT INTO Applicant4
VALUES (755555555, 2, 'Physics', 'Tom', '5800 Puget Drive');

INSERT INTO Applicant4
VALUES (780808080, 4, 'Finance', 'Josh', '6561 Cambie Street');

INSERT INTO Applicant4
VALUES (943434343, 3, 'Accounting', 'Jack', '3455 Agronomy Rd');

INSERT INTO Applicant4
VALUES (722222222, 3, 'Medicine', 'Jane', '5981 Davie Drive');

INSERT INTO Applicant4
VALUES (965748374, 3, 'Nutrition', 'Amanda', '7999 Penguin Drive');

INSERT INTO Degree
VALUES (3, 'Psychology','Arts');

INSERT INTO Degree
VALUES (3, 'Computer Science','Science');

INSERT INTO Degree
VALUES (3, 'Business','Commerce');

INSERT INTO Degree
VALUES (4, 'Economics','Arts');

INSERT INTO Degree
VALUES (5,'History','Arts');

INSERT INTO Intern
VALUES (777777777, 1);

INSERT INTO Intern
VALUES (788888888, 2);

INSERT INTO Intern
VALUES (977777777, 1);

INSERT INTO Intern
VALUES (755555555, 1);

INSERT INTO Intern
VALUES (999999999, 2);

INSERT INTO Part_Time
VALUES(999999999, 40);

INSERT INTO Part_Time
VALUES(799999999, 40);

INSERT INTO Part_Time
VALUES(780808080, 40);

INSERT INTO Part_Time
VALUES(943434343, 40);

INSERT INTO Full_Time
VALUES(722222222, 0);

INSERT INTO Full_Time
VALUES(965748374, 0);

INSERT INTO Attended
VALUES (777777777, 'University of British Columbia');

INSERT INTO Attended
VALUES (788888888, 'University of Toronto');

INSERT INTO Attended
VALUES (977777777, 'University of Washington');

INSERT INTO Attended
VALUES (999999999, 'University of Alberta');

INSERT INTO Attended
VALUES (799999999, 'University of British Columbia');




