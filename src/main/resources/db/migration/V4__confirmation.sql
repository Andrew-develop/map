create table confirmations (
  id                    uuid,
  code                  int not null,
  expires_at            timestamp not null,
  token                 varchar(255),
  user_id               bigint not null,
  primary key (id),
  foreign key (user_id) references users (id) on delete cascade
);