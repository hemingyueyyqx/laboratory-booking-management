
# select * from user u
# join course c on c.teacher_id=u.id
# join appointment a on a.course_id=c.id
# join lab l l.id=a.lab_id
# where u.id='1';
explain
select * from appointment a where a.teacher ->> '$.id'='1';
/**查的多改的少*/
explain
select * from  appointment a where a.teacher ->> '$.id'='1' and a.course ->> '$.id'='1';
explain
select * from  appointment a where a.lab ->> '$.id'= '1' and a.week=1 and a.dayofweek=1 and a.section=1;
explain
/**基于周/星期/节，查询可用实验室 查询不存在必须得在关联的时候查出来，在where做标识为空*/
select * from `2022222979`.lab l
left join appointment a
on l.id=a.lab ->> '$.id' and semester='24-1' and a.week=1 and a.dayofweek=1 and a.section=1
where a.lab ->> '$.id' is null and l.state=1;
/**空闲实验室*/
/**冗余*/

explain
select * from lab l left join  appointment a on l.id = a.lab->>'$.id'
and a.teacher ->> '$.id' = '1' and a.course->>'$.id' = '1'
where a.lab ->> '$.id' is null and l.state=1;

show index in appointment;