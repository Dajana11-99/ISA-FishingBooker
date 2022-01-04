/*INSERT INTO users (role, id, activation_url, city, country, latitude, longitude, street_and_num, email, enabled, last_name, last_password_reset_date, name, password, phone_num, is_predefined, registration_reason) values ('ADMIN',1,null, 'Novi Sad','Serbia',45.267136,19.833549, 'Ljubice Ravasi 2a','dajanazlokapa1@gmail.com',true, 'Zlokapa',null,'Dajana','Dajana99!','+3810616789856',true,null);*/



INSERT INTO AUTHORITY (id ,name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (id ,name) VALUES (2, 'ROLE_CABINOWNER');
INSERT INTO AUTHORITY (id ,name) VALUES (3, 'ROLE_BOATOWNER');
INSERT INTO AUTHORITY (id ,name) VALUES (4, 'ROLE_FISHING_INSTRUCTOR');

/*INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 1);*/
