create table presets (
  id                    bigserial,
  name                  varchar(80) not null,
  user_id               bigint not null,
  primary key (id),
  foreign key (user_id) references users (id)
);

create table presets_rules (
  preset_id             bigint not null,
  rule_id               int not null,
  primary key (preset_id, rule_id),
  foreign key (preset_id) references presets (id),
  foreign key (rule_id) references rules (id)
);

create table projects (
  id                    serial,
  name                  varchar(80) not null,
  user_id               bigint not null,
  primary key (id),
  foreign key (user_id) references users (id)
);

create table revisions (
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