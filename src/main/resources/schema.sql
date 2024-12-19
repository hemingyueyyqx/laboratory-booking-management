create table if not exists `user`
(
    id char(26) primary key ,
    name varchar(6) not null ,/**字符6*/
    account varchar(10) not null  ,
    password char(60) not null ,/**指定算法60*/
    role char(4) not null,/**指定四位*/
    telephone char(11) not null ,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp  on update current_timestamp,
    unique (account) /**自带索引*/
    );

create table if not exists `course`
(

    id char(26) primary key ,
    name varchar(20) not null ,
    quantity tinyint unsigned not null ,/**人数*/
    semester char(4) not null ,/**学期，用下拉框，让老师们选*/
    clazz varchar(30) not null,/**上课班级*/
    type tinyint unsigned check ( 0 or 1),/**必修课，选修课*/
    teacher_id char(26) not null,
    experiment_hour tinyint unsigned not null,/**实验学时,到时候可以校验一下（当老师选的学时还有剩余时）*/
    index(teacher_id,semester)
    );

create table if not exists `appointment-json` (
    id char(26) primary key,
    teacher json  not null comment '{id, name}',/**要不要有该字段*/
    course json not null comment '{id,name}',
    lab json not null comment '{id,name}',
    semester char(4) not null ,
    nature varchar(4) not null ,/** 性质，约定为课程，临时预约等。到时候前端就用那个输入多选框约束*/
    week tinyint unsigned not null,/**非负小整数 空间换时间 查询高效*/
    dayofweek tinyint unsigned not null /**非负小整数 */,
    section tinyint unsigned not null /**非负int section*/,
    unique((cast(lab ->> '$.id' as char(26)) collate utf8mb4_bin),semester,week,dayofweek,section),/**唯一约束 复合索引*/
    index ((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))
    );
# a表八条数据才能命中索引，否则全表扫描，MySQL优化查询
create table if not exists `appointment` (
id char(26) primary key,
teacher json  not null comment '{id, name}',/**要不要有该字段*/
course json not null comment '{id,name}',
# lab json not null comment '{id,name}',
lab_id char(26) not null ,/**json冗余形式on关联时命中不了索引，针对MySQL索引字段使用函数，则索引失效，MySQL的bug*/
lab_name varchar(10) not null ,
semester char(4) not null ,
nature varchar(4) not null ,/** 性质，约定为课程，临时预约等。到时候前端就用那个输入多选框约束*/
week tinyint unsigned not null,/**非负小整数 空间换时间 查询高效*/
dayofweek tinyint unsigned not null /**非负小整数 */,
section tinyint unsigned not null /**非负int section*/,
unique(lab_id,semester,week,dayofweek,section),/**唯一约束 复合索引*/
index ((cast(teacher ->> '$.id' as char(26)) collate utf8mb4_bin),(cast(course ->> '$.id' as char(26)) collate utf8mb4_bin))
);

create table if not exists `lab` (
    id char(26) primary key ,
    name varchar(10) not null , /**varchar10???人工智能实验室*/
    state tinyint unsigned not null default 1,/**被维修还是可用 不一定一种状态 1是可用状态*/
    quantity tinyint unsigned null ,/**可空 非负0-255*/
    description varchar(500) null, /***可空*/
    manager json comment '{id, name}' null , /**可能为空*/
    index(state)
    );

create table if not exists `news` (
    id char(26) primary key ,
    title varchar(50) not null ,
    content varchar(2000) not null ,
    author varchar(50) not null ,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
    );

# INSERT INTO `course` (id, name, quantity,  semester,  clazz, type, teacher_id,  experiment_hour) VALUES
# ('1', '高等数学', 120, '24-1', '数学与应用数学', 1, 1, 8),
# ('2', '大学物理', 90, '24-1', '物理学', 1, 2, 8),
# ('3', '编程基础', 75, '24-1', '计算机科学与技术', 1, 1, 8),
# ('4', '工程制图', 60, '24-1', '机械工程', 1, 3, 8),
# ('5', '基础化学', 80, '24-1', '化学工程与工艺', 1, 2, 8),
# ('6', '英语听力', 100, '24-1', '英语', 1, 1, 8);



