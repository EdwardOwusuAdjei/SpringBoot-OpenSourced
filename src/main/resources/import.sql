insert into user (id, country, email, firstname, lastname, mifosaccountnumber, mobile, password, securityanswer, securityquestion) values (1, 'ghana', 'james@u.com', 'James', 'Lee', null, '0244123456', '$2a$10$gXJGzVY809dqz7LVAZYKN.TnGs/06JyFYfVcTQ.ypMyKkf4IaX.LC', null, null);

insert into role(id, name) values(1,'Admin');
insert into role(id, name) values(2, 'User');
insert into user_role(user_id,role_id) values(1,1);