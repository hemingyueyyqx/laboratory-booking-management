create table if not exists `user`
(
    id char(26) primary key ,
    name varchar(6) not null ,
    account char(10) not null ,
    password char(60) not null ,
    role char(4) not null,/**乱码长度为四*/
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,
    unique (account)
);

create table if not exists `course`
(

    id char(26) primary key ,/**!*/
    name varchar(20) not null ,/**!*/
    quantity int unsigned not null ,/** !学生数*/
    semester char(11) not null ,/**学期，用下拉框，让老师们选*/
    major varchar(30) not null ,/**专业*/
    grade int unsigned not null ,/**年级*/
    class tinyint unsigned not null,/**上课班级*/
    type tinyint unsigned check ( 0 or 1),/**必修课，选修课*/
    teacher_id char(26) not null,/**!*/
    credit_hour tinyint unsigned not null, /**总学时*/
    experiment_hour tinyint unsigned not null,/**实验学时,到时候可以校验一下（当老师选的学时还有剩余时）*/

    index(teacher_id)
);

/**预约表*/
create table if not exists `appointment` (
   id char(26) primary key,/**!*/
    teacher json  not null comment '{id, name}',
    course json not null comment '{id,name}',
    lab json not null comment '{id, name}',
   nature varchar(30) not null ,/** 性质，约定为课程，临时预约等。到时候前端就用那个输入多选框约束*/
   week tinyint unsigned not null,/**!非负小整数*/
   dayofweek tinyint unsigned not null ,/**!非负小整数 */
/** 上课节次 */
   section tinyint unsigned not null ,/**!非负int*/
   unique((cast(lab ->> '$.id' as char(26)) collate utf8mb4_bin),week,dayofweek,section),

   index ((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))


);

create table if not exists `lab` (
     id char(26) primary key ,/**!*/
     name varchar(10) not null ,/**!如大数据实验室等*/
     state tinyint unsigned not null default 1,/**被维修还是可用还是啥啥啥*/
     quantity int unsigned ,/**!容纳数*/
     description text,/**!*/
     manager json comment '{id, name}',/**!*/
     index(state,quantity)
);

    create table if not exists `news` (
      id char(26) primary key ,
      title varchar(50) not null ,
      content text not null ,
      author varchar(50) not null ,
      create_time datetime not null default current_timestamp,
      update_time datetime not null default current_timestamp on update current_timestamp,
      index(title)
);
