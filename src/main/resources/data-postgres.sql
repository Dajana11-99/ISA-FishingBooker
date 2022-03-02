/*sifra: 123*/INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, username,        enabled, last_name, last_password_reset_date, name, password, phone_num,reason_for_deleting,changed_password, is_predefined, registration_reason) values ('ADMIN',1,null, 'Novi Sad','Serbia',45.267136,19.833549, 'Ljubice Ravasi 2a','dajanazlokapa1@gmail.com',true, 'Zlokapa','2022-01-04 15:31:53.899','Dajana','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','+3810616789856','',true,true,null);
/*sifra: 123*/INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, username,       enabled, last_name, last_password_reset_date, name, password, phone_num,reason_for_deleting, is_predefined, registration_reason) values ('CABIN OWNER',2,null, 'Novi Sad','Serbia',45.267136,19.833549, 'ULICA','co@gmail.com',true, 'Vlasnik','2022-01-04 15:31:53.899','Vikendice','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','+3810616789856','',null,'Da zaradim malo.');
/*sifra: 123*/INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, username,        enabled, last_name, last_password_reset_date, name, password, phone_num,reason_for_deleting, is_predefined, registration_reason) values ('BOAT OWNER',3,null, 'Novi Sad','Serbia',45.267136,19.833549, 'ULICA','bo@gmail.com',true, 'Vlasnik','2022-01-04 15:31:53.899','Broda','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','+3810616789856','',null,'Da zaradim malo i ja kad vec imam brod.');
/*sifra: 123*/INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, username,rating, enabled, last_name, last_password_reset_date, name, password, phone_num,reason_for_deleting, is_predefined, registration_reason) values ('FISHING INSTRUCTOR',4,null, 'Novi Sad','Serbia',45.267136,19.833549, 'ULICA','fi@gmail.com', 4.5,true, 'Vlasnik','2022-01-04 15:31:53.899','Pecaros','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','+3810616789856','',null,'Da zaradim malo iako ne znam da pecam.');
/*sifra: 123*/INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, username,         enabled, last_name, last_password_reset_date, name, password, phone_num,reason_for_deleting,changed_password, is_predefined, registration_reason) values ('ADMIN',5,null, 'Novi Sad','Serbia',45.267136,19.833549, 'Bilo sta','proba@gmail.com',true, 'ZPrezime','2022-01-04 15:31:53.899','Ime','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','+3810616789856','',false,false,null);
/*sifra: 123*/INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, username,         enabled, last_name, last_password_reset_date, name, password, phone_num,reason_for_deleting, is_predefined) values ('CLIENT',6,null, 'Novi Sad','Serbia',45.267136,19.833549, 'ULICA','cl@gmail.com', true, 'Klijent','2022-01-04 15:31:53.899','Klijentovski','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','+3810616789856','',null);

INSERT INTO AUTHORITY (id ,name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (id ,name) VALUES (2, 'ROLE_CABINOWNER');
INSERT INTO AUTHORITY (id ,name) VALUES (3, 'ROLE_BOATOWNER');
INSERT INTO AUTHORITY (id ,name) VALUES (4, 'ROLE_FISHING_INSTRUCTOR');
INSERT INTO AUTHORITY (id ,name) VALUES (6, 'ROLE_CLIENT');


INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 6);
/*
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 1);*/
