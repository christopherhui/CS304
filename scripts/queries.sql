-- Insert new job posting
INSERT INTO Job (Job_Number, Company_Name, Job_Title, Description)
VALUES(2020, 'Rona', 'Public Relations Specialist', 'Write news articles, maintain social media posts');

-- Delete job posting
DELETE FROM Job
WHERE Job_Number = 2000;

--Update application status
UPDATE Application_Through_For
SET Status = 'Closed'
WHERE SIN = 977777777;

--Select job titles from a specific company
SELECT Job_Title
FROM Job
WHERE Company_Name = 'Amazon';

--Select company names from job postings
SELECT Company_Name
FROM Job;

--Find locations for specific job position
SELECT Requirement4.Location
FROM Requirement4
         INNER JOIN Job ON Requirement4.Job_Number = Job.Job_Number
WHERE Job.Job_Title = 'Accountant';

--Count total number of jobs available
SELECT count(*)
FROM Job;

--Count total number of jobs from each company
SELECT count(*)
FROM Job
GROUP BY Company_Name;

--Find SIN of the job applicant who applied to all companies
SELECT a.SIN
FROM Applicant4 a
WHERE NOT EXISTS (SELECT j.Company_Name
                  FROM Job j
                  MINUS (SELECT atf.Company_Name
						FROM Application_Through_For atf
						WHERE atf.SIN = a.SIN));

SELECT atf.SIN FROM APPLICATION_THROUGH_FOR atf WHERE NOT EXISTS
					(SELECT COMPANY_NAME FROM COMPANY MINUS
					(SELECT DISTINCT COMPANY_NAME FROM APPLICATION_THROUGH_FOR atf2 WHERE atf2.SIN = atf.SIN));