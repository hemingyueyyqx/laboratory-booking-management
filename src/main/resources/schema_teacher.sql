create table if not exists `user`
(
    id          char(19) primary key,
    name        varchar(6) not null,
    account     char(10)   not null,
    password    char(60)   not null,
    role        char(4)    not null,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,

    unique (account)
);

create table if not exists `course`
(
    id         char(19) primary key,
    name       varchar(6)       not null,
    quantity   tinyint unsigned not null,
    lesson     tinyint unsigned not null,
    teacher_id char(19)         not null,
    index (teacher_id)
);

create table if not exists `lab`
(
    id          char(19) primary key,
    name        varchar(10)      not null,
    manage      varchar(6)       null,
    quantity    tinyint unsigned null,
    description varchar(200)     null,
    state tinyint unsigned not null default 1,
    index (state)
);

create table if not exists `appointment`
(
    id        char(19) primary key,
    lab_id    char(19)         not null,
    week      tinyint unsigned not null,
    dayofweek tinyint unsigned not null,
    section   tinyint unsigned not null,
    course_id char(19)         not null,

    unique (lab_id, week, dayofweek, section),
    index (course_id)
);

create table if not exists `appointment_json`
(
    id        char(19) primary key,
    course    json             not null comment '{id, name}',
    teacher   json             not null comment '{id, name}',
    lab       json             not null comment '{id, name}',
    week      tinyint unsigned not null,
    dayofweek tinyint unsigned not null,
    section   tinyint unsigned not null,

    unique ((cast(lab ->> '$.id' as char(19) ) collate utf8mb4_bin), week, dayofweek, section),
    index ((cast(teacher ->> '$.id' as char (19)) collate utf8mb4_bin)),
    index ((cast(course ->> '$.id' as char (19)) collate utf8mb4_bin))
)