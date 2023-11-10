create table rules (
  id                    serial,
  name                  varchar(80) unique not null,
  primary key (id)
);

create table rule_packs (
  id                    serial,
  name                  varchar(80) unique not null,
  price                 int not null,
  primary key (id)
);

create table rule_packs_rules (
  rule_pack_id          int not null,
  rule_id               int not null,
  primary key (rule_id),
  foreign key (rule_pack_id) references rule_packs (id),
  foreign key (rule_id) references rules (id)
);

create table users_rule_packs (
  user_id               bigint not null,
  rule_pack_id          int not null,
  primary key (user_id, rule_pack_id),
  foreign key (user_id) references users (id),
  foreign key (rule_pack_id) references rule_packs (id)
);

insert into rules (name)
values
('RULE_TWO_IDENTICAL_WORDS'), ('RULE_OUTSIDE_FIELDS'), ('RULE_LITLINK'), ('RULE_SHORT_DASH'), ('RULE_MEDIUM_DASH'),
 ('RULE_LONG_DASH'), ('RULE_UNSCIENTIFIC_SENTENCE'), ('RULE_CLOSING_QUOTATION'), ('RULE_OPENING_QUOTATION'),
  ('RULE_MULTIPLE_LITLINKS'), ('RULE_BRACKETS_LETTERS'), ('RULE_CITATION'), ('RULE_NO_TASKS'), ('RULE_TASKS_MAPPING'),
   ('RULE_LONG_SENTENCE'), ('RULE_SECTION_NUMBERING_FROM_0'), ('RULE_SINGLE_SUBSECTION'),
    ('RULE_TABLE_OF_CONTENT_NUMBERS'), ('RULE_SYMBOLS_IN_SECTION_NAMES'), ('RULE_DISALLOWED_WORDS'),
     ('RULE_INCORRECT_ABBREVIATION'), ('RULE_SHORTENED_URLS'), ('RULE_URLS_UNIFORMITY'), ('RULE_ORDER_OF_REFERENCES'),
      ('RULE_VARIOUS_ABBREVIATIONS'), ('RULE_SECTIONS_ORDER'), ('RULE_LOW_QUALITY_CONFERENCES'),
       ('RULE_NO_SPACE_AFTER_PUNCTUATION'), ('RULE_SPACE_BEFORE_PUNCTUATION'), ('RULES_SPACE_AROUND_BRACKETS'),
        ('RULES_SMALL_NUMBERS'), ('RULES_SECTION_SIZE');

insert into rule_packs (name, price)
values
('Incorrect dashes usage', 0);

insert into rule_packs_rules (rule_pack_id, rule_id)
values
(1, 4),
(1, 5),
(1, 6);

insert into users_rule_packs (user_id, rule_pack_id)
values
(1, 1),
(2, 1);