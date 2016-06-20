CREATE SEQUENCE "DIGITAL_THREAD".PROJECT_DETAILS_SEQ;
CREATE TABLE "DIGITAL_THREAD".PROJECT_DETAILS(projectid INTEGER default nextval('digital_thread.PROJECT_DETAILS_SEQ'), url VARCHAR(1000), title varchar(255), projectName  varchar(255), imgSrc VARCHAR(1000),projectDescription varchar(4000) , imageName varchar(255), image bytea,  docName varchar(255), doc bytea );

