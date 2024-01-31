create table confirmations (
  id                    uuid,
  code                  int not null,
  user_id               bigint not null,
  expires_at            timestamp not null,
  primary key (id),
  foreign key (user_id) references users (id) on delete cascade
);