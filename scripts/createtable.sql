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
    (Location		CHAR(20)
    Citizenship		CHAR (20)
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
VALUES ('Vancouver BC' , 'Canada');

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


