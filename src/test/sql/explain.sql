/**????*/
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
select * from  appointment a where a.lab_id= '1' and semester='24-1' and a.week=1 and a.dayofweek=1 and a.section=1;
explain
/**基于学期/周/星期/节，查询可用实验室 查询不存在必须得在关联的时候查出来，在where做标识为空*/
select * from `2022222979`.lab l
left join appointment a
on l.id=a.lab_id and semester='24-1' and a.week=1 and a.dayofweek=1 and a.section=1
where a.lab_id is null and l.state=1;
/**空闲实验室*/
/**冗余*/

explain
select * from lab l left join  appointment a on l.id = a.lab_id
and a.teacher ->> '$.id' = '1' and a.course->>'$.id' = '1'
where a.lab_id is null and l.state=1;

show index in appointment;
# 基于学期/周/星期，查询用空闲节的可用教室
explain
select * from lab l
left join appointment a
on l.id = a.lab_id and semester='24-1' and a.week=1 and a.dayofweek=1
where a.lab_id is null and a.section is null and l.state=1;

-- 1.指定老师的课表,查课程信息，班级，实验室，周次，星期，节次，a表，并course表查班级?
explain
select * from appointment a
where a.teacher ->> '$.id' = '1' and a.course ->> '$.id' = '1';



-- 2.基于学期/周/星期/节，查询可用教室
explain
select * from lab l
left join appointment a
on l.id=a.lab_id and a.semester='24-1' and a.week=1 and a.dayofweek=2 and a.section=3
where a.lab_id is null and l.state = 1;

-- 3.基于周/星期，查询空闲节可用教室
# CROSS JOIN 是一种笛卡尔积连接，将 lab 表中的每一行与 s 表中的每一行连接。s 表是一个内联的子查询，它生成了一个包含四个值（1、2、3、4）的虚拟表，表示四个节次。
# 这个 CROSS JOIN 的目的是生成一个实验室和每个节次的组合。
# GROUP_CONCAT 是一个聚合函数，它将同一组内的多个节次连接成一个字符串。
# ORDER BY s.section 确保返回的节次是按升序排列的。
# 结果会给出一个字段 free_sections，表示每个实验室的未预约节次，多个节次用逗号隔开。
explain
SELECT
l.id AS lab_id,
l.name AS lab_name,
GROUP_CONCAT(s.section ORDER BY s.section) AS free_sections
FROM
    lab l
        CROSS JOIN
    (SELECT 1 AS section UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s
        LEFT JOIN
    appointment a
    ON
        l.id = a.lab_id
            AND a.semester = '24-1' -- 替换为具体学期
            AND a.week = 1        -- 替换为具体周次
            AND a.dayofweek = 2    -- 替换为具体星期（1=周一，7=周日）
            AND a.section = s.section
WHERE
    l.state = 1 -- 实验室状态为可用
  AND a.id IS NULL -- 节次无预约
GROUP BY
    l.id, l.name;

-- 4.根据老师id查出该老师所有的预约课程
# JSON_EXTRACT(a.teacher, '$.id') AS teacher_id：
# 从 teacher 字段中提取 id，并将其命名为 teacher_id。
# 假设 teacher 字段是一个 JSON 格式的数据，$.id 表示提取 teacher 对象中的 id 字段。
explain
SELECT
    a.id AS appointment_id,
    JSON_EXTRACT(a.teacher, '$.id') AS teacher_id,
    JSON_EXTRACT(a.teacher, '$.name') AS teacher_name,
    JSON_EXTRACT(a.course, '$.id') AS course_id,
    JSON_EXTRACT(a.course, '$.name') AS course_name,
    a.semester,
    a.nature,
    a.lab_id,
    a.lab_name,
    a.week,
    a.dayofweek,
    a.section
FROM
    appointment a
WHERE
    JSON_EXTRACT(a.teacher, '$.id') = '1'; -- 替换为具体老师ID

-- 5.根据老师id和指定学期查出该老师该学期所有的预约课程
SELECT
    a.id AS appointment_id,
    JSON_EXTRACT(a.teacher, '$.id') AS teacher_id,
    JSON_EXTRACT(a.teacher, '$.name') AS teacher_name,
    JSON_EXTRACT(a.course, '$.id') AS course_id,
    JSON_EXTRACT(a.course, '$.name') AS course_name,
    a.semester,
    a.nature,
    a.lab_id,
    a.lab_name,
    a.week,
    a.dayofweek,
    a.section
FROM
    appointment a
WHERE
    JSON_EXTRACT(a.teacher, '$.id') = '1' -- 替换为具体老师ID
  AND a.semester = '24-1'; -- 替换为具体学期

-- 6.根据课程id和老师id查某个老师某个学期某门课的预约情况
SELECT
    a.id AS appointment_id,
    JSON_EXTRACT(a.teacher, '$.id') AS teacher_id,
    JSON_EXTRACT(a.teacher, '$.name') AS teacher_name,
    JSON_EXTRACT(a.course, '$.id') AS course_id,
    JSON_EXTRACT(a.course, '$.name') AS course_name,
    a.semester,
    a.nature,
    a.lab_id,
    a.lab_name,
    a.week,
    a.dayofweek,
    a.section
FROM
    appointment a
WHERE
    JSON_EXTRACT(a.teacher, '$.id') = '1' -- 替换为具体老师ID
  AND JSON_EXTRACT(a.course, '$.id') = '1' -- 替换为具体课程ID
  AND a.semester = '24-1'; -- 替换为具体学期

# 7、加载当前教师全部课程，基于id，course表
select * from `2022222979`.course c
where c.teacher_id=1;
# 8、加载人数可用实验室，基于教师id.课程id,查可用实验室?
select a.lab_name from `2022222979`.appointment a
join `2022222979`.lab l
on l.id = a.lab_id
join `2022222979`.course c
on a.course ->> '$.id'=c.id
where a.teacher ->> '$.id' = '1' and a.course->>'$.id' = '1'and l.state=1 and c.quantity<l.quantity;
# 9、加载全部可用实验室，基于教师id.课程id,仅加载名称
select l.name from `2022222979`.lab l
where l.state = 1;
# 10、基于指定教室，加载课表
select * from `2022222979`.appointment a
where a.lab_name='901';
# 11、基于教师id，课程id，查询预约信息
explain
select *
from `2022222979`.appointment a
where a.teacher ->> '$.id'='1' and a.course ->> '$.id'='1';
# 12、基于老师id，课程id，查所有周次 全索引index
explain
SELECT
    CAST(teacher ->> '$.id' AS CHAR(26)) AS teacher_id,
    CAST(course ->> '$.id' AS CHAR(26)) AS course_id,
    CONCAT(GROUP_CONCAT(week ORDER BY week ASC)
    ) AS weeks
FROM
    appointment a
 where a.teacher ->> '$.id'='1' and a.course ->> '$.id'='1'
GROUP BY
    teacher_id, course_id;
# 13、基于老师id，课程id，查课表
explain
SELECT
   a.*,c.*

FROM
    appointment a
       left JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'and a.semester='24-2';
explain
SELECT
    a.teacher ->>'$.name' as teacherName,
    c.name as courseName,
    c.clazz ,
    CONCAT(GROUP_CONCAT(a.week ORDER BY a.week ASC)) AS weeks,
    c.experiment_hour,
    a.lab_name
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
GROUP BY
    a.teacher ->> '$.id', a.course ->> '$.id', teacherName,courseName, c.clazz, c.experiment_hour, a.lab_name;
explain
SELECT
    a.dayofweek,
    a.section,
    a.teacher,
    a.course,
    c.clazz,
    JSON_ARRAYAGG(a.week) AS weeks,  -- 使用JSON_ARRAYAGG函数将week值聚合成JSON数组
    c.experiment_hour,
    a.lab_id,
    a.lab_name
FROM
    appointment a
        JOIN
    course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
WHERE
    a.teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
GROUP BY
    a.teacher ->> '$.id', a.course ->> '$.id',a.dayofweek,a.section, a.teacher,a.course, c.clazz, c.experiment_hour, a.lab_id,a.lab_name;
# 14、基于老师id，课程id，查a表中有几条记录，乘2即已经选了多少课时lab
explain
SELECT COUNT(*) AS record_count
FROM `appointment`
WHERE teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
  AND course ->> '$.id' = '1';
# 15、基于老师id，课程id，渲染状态可用，人数可用教室
explain
select l.id,l.name,l.state,l.quantity,l.description,l.enable_equipment,l.manager from lab l
join appointment a on a.lab_id = l.id
join course c on a.course ->> '$.id' = c.id
where l.state = 1 and a.teacher ->> '$.id' = '01JFMF27TJ1TF9YQ240HG7P7EW'and a.course ->> '$.id'='01JG8N7SWX4V02GKEQCJ8K81S8' and c.quantity < l.quantity;
explain
SELECT l.*
FROM `lab` l, `course` c
WHERE l.quantity >= c.quantity
  AND c.teacher_id = '01JFHY1JRC5BJN919YEHQYXWAR'
  AND c.id = '2'and l.state =1;
# 16、基于老师id，课程id，渲染状态可用，人数不够教室
explain
SELECT l.*
FROM lab l
join  course c on l.quantity < c.quantity
WHERE c.teacher_id = '01JFHY1JRC5BJN919YEHQYXWAR'and c.id = '2'and l.state =1;

delete from course c where c.teacher_id='10' and c.id='4';
# 17、基于老师id，课程id查预约表中记录
SELECT COUNT(*) AS record_count
FROM `appointment`
WHERE teacher ->> '$.id' = '01JFJ5CWY6FD4XTTHR42FBS6A4'
  AND course ->> '$.id' = '1';
# 18、基于实验室id，渲染预约表
explain
select * from appointment a where a.lab_id = '1';
select a.*,c.*
from appointment a
         join  course c ON a.teacher ->> '$.id' = c.teacher_id AND a.course ->> '$.id' = c.id
