create table subscriptions (
  id                    serial,
  name                  varchar(80) not null,
  price                 int not null,
  projects              int not null,
  revisions_per_day     int not null,
  presets               int not null,
  primary key (id)
);

create table users (
  id                    bigserial,
  name                  varchar(30) not null,
  password              varchar(80) not null,
  email                 varchar(50) unique,
  subscription_id       int not null,
  primary key (id),
  foreign key (subscription_id) references subscriptions (id)
);

create table roles (
  id                    serial,
  name                  varchar(50) not null,
  primary key (id)
);

create table users_roles (
  user_id               bigint not null,
  role_id               int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id) on delete cascade,
  foreign key (role_id) references roles (id) on delete cascade
);

insert into subscriptions (name, price, projects, revisions_per_day, presets)
values
('base', 0, 0, 1, 3), ('Stage1', 199, 1, 5, 5);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN'), ('SOMETHING');

insert into users (name, password, email, subscription_id)
values
('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com', 1),
('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com', 2);

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);