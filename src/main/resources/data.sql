
INSERT INTO "user" (id, isactive, firstname, lastname,  password, username) VALUES (1, true, 'Olga', 'Kurilenko', 'WRqqRQMsoy', 'Olga.Kurilenko'), (2, true, 'Kim','Johnson', '555555', 'Kim.Johnson'), (3, true, 'Tomas','Kuk', '12345', 'Tomas.Kuk'), (4, true, 'George','TheThird', '54321', 'George.TheThird'), (5, true, 'Monica', 'Dobs', '55555', 'Monica.Dobs'), (6, true, 'Wallace', 'Tim', '66666', 'Wallace.Tim'), (7, true, 'Tom','Robins', '77777', 'Tom.Robins'), (8, true, 'Bob','Getty', '88888', 'Bob.Getty');

INSERT INTO trainee (traineeid, address, dateofbirth, user_id) VALUES (1, 'California', '1986-12-30', 1), (2, 'Chicago', '1972-02-01',2), (3, 'Sweden Oslo', '1962-05-05',3), (4, 'UK', '1900-12-30',4);

INSERT INTO trainingtype (id, trainingtype) VALUES (1, 'YOGA'), (2, 'FITNESS'),(3, 'ZUMBA');

INSERT INTO trainer (trainerid, specialization_id, user_id)  VALUES (1,1,5), (2,2,6), (3,3,7), (4,2,8);

