CREATE DATABASE cs595database;

CREATE TABLE DEVICE (Device_id INT (10), Device_type varchar (30));
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (1, 'Wrist Sensor');
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (2, 'Wrist Sensor');
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (3, ' Wrist Sensor ');
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (4, ' Wrist Sensor ');
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (5, ' Wrist Sensor ');
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (6, ' Wrist Sensor ');
INSERT INTO DEVICE (Device_id, Device_Type) VALUES (7, ' Wrist Sensor ');

CREATE TABLE DEVICE_MOTION(Device_id INT (10), Patient_id INT (10), Patient_Name varchar (30), DO_AX1 INT (10), DO_AX2 INT (10), DO_AX3 INT (10), DO_GX1 INT (10), DO_GX2 INT (10), DO_GX3 INT (10),  DATE_TIME TIMESTAMP );
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (1, 123, 'John', 1,1,1,1,2,3, '2015:07:20 10:30:20');
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (2, 124, 'Jack', 1,1,2,1,2,3, '2015:07:21 15:20:30');
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (3, 125, 'Michel', 1,2,1,1,2,3, '2015:07:22 04:25:30');
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (4, 126, 'Lara', 1,2,2,1,2,3, '2015:07:23 11:35:10');
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (5, 127, 'George', 2,1,1,1,2,3, '2015:07:24 14:45:20');
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (6, 128, 'Ron', 2,1,2,1,2,3, '2015:07:25 16:45:10');
INSERT INTO DEVICE_MOTION (Device_id, Patient_id, Patient_Name, DO_AX1, DO_AX2, DO_AX3, DO_GX1, DO_GX2, DO_GX3, DATE_TIME) VALUES (7, 129, 'Eric', 2,2,1,1,2,3, '2015:07:26 18:25:20');

CREATE TABLE FALL_DATA (Fall_id INT (10), Fall_Type varchar  (30), Fall_Time TIMESTAMP, Patient_id INT (10));
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(131, 'Not Adverse', '2015-07-20 10:30:20',  123);
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(132, 'Not Adverse', '2015-07-21 15:20:30',  124);
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(133, 'Not Adverse', '2015-07-22 04:25:30',  125);
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(134, 'Adverse', '2015-07-23 11:35:10',  126);
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(135, 'Not Adverse', '2015-07-24 14:45:20',  127);
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(136, 'Adverse', '2015-07-25 16:45:10',  128);
INSERT INTO FALL_DATA (Fall_id, Fall_Type, Fall_Time, Patient_id) VALUES(137, 'Adverse', '2015-07-26 18:25:20',  129);

CREATE TABLE PATIENT (Patient_Name varchar (30), Patient_id INT (10), Device_id INT (10), ADDRESS varchar (300), Phone INT (10));
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('John', 123, 1, 'Suite_Four, Mowry, Fremont', 510320421);
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('Jack', 124, 2, 'Suite_Five, Stv_Blvd, Fremont', 510325426);
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('Michel', 125, 3, 'Floor_Five, Stv_Place, Fremont', 510327427);
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('Lara', 126, 4, 'House_Five, Stv_St, Fremont', 510329429);
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('George', 127, 5, 'Apt_Six, Alameda, Fremont', 510344433);
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('Ron', 128,6, 'Apt_Six, Warm_Springs, Fremont', 510325455);
INSERT INTO PATIENT (Patient_Name, Patient_id, Device_id, ADDRESS, Phone) VALUES ('John', 129, 7, 'Apt_Seven, Warm_Springs, Fremont', 510377466);

CREATE TABLE STAFF (Staff_id INT (10), Staff_Name varchar (30), Staff_Designation varchar (30), Staff_Phone varchar (15), Staff_Email varchar (30));
INSERT INTO STAFF (Staff_id, Staff_Name, Staff_Designation, Staff_Phone, Staff_Email) VALUES(111, 'Emma', 'Nurse', '2247700674', 'emmaw9396@gmail.com');
