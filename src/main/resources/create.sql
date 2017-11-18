
create table if not exists users (
 id int not null AUTO_INCREMENT,
 username varchar(255) not null,
 password varchar(255) not null,
 PRIMARY KEY (ID)
);

create table if not exists contacts (
 id int not null AUTO_INCREMENT,
 userid int not null,
 first_name varchar(255),
 last_name varchar(255),
 phones VARCHAR(1024),
 PRIMARY KEY (ID),
  FOREIGN KEY (userid) REFERENCES users(id)
   ON DELETE CASCADE
   ON UPDATE CASCADE
);

merge into users (ID, username, password)
values (1, 'artem', '10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=');

merge into users (ID, username, password)
values (2, 'user', '10/w7o2juYBrGMh32/KbveULW9jk2tejpyUAD+uC6PE=');

merge into contacts(ID, userid, first_name, last_name, phones)
values (0, 1, 'Mother', null, '+380501233445')