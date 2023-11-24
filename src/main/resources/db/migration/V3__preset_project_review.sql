create table projects (
  id                    serial,
  name                  varchar(80) not null,
  user_id               bigint not null,
  primary key (id),
  foreign key (user_id) references users (id)
);

create table reviews (
  id                    bigserial,
  filepath              varchar(80) not null,
  rev_date              timestamp not null,
  user_id               bigint not null,
  preset_id             bigint not null,
  project_id            bigint,
  primary key (id),
  foreign key (user_id) references users (id),
  foreign key (preset_id) references presets (id),
  foreign key (project_id) references projects (id)
);