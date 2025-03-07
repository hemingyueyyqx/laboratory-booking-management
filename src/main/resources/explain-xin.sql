/**指定老师的课表*/
/**
explain
select * from user u
  join `2022222994`.course c on c.teacher_id = u.id
  join appointment a on c.id = a.course_id
  join `2022222994`.lab on a.lab_id = lab.id
where u.id = '1';
*/

-- 1.指定老师的课表
explain
select * from appointment a
where a.teacher ->> '$.id' = '1';



-- 2.基于学期/周/星期/节，查询可用教室
explain
select * from lab l
left join appointment a
on l.id=a.lab_id and a.semester='25-1' and a.week=1 and a.dayofweek=2 and a.section=3
where a.lab_id is null and l.state = 1;

-- 3.基于周/星期，查询空闲节可用教室
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



-- 插入第一条记录
INSERT INTO `lab` (`id`, `name`, `state`, `quantity`, `description`, `manager`, `enable_equipment`)
VALUES ('00000000000000000000000001', '901', 1, 50, '这是一个功能齐全的实验室', '{"id": "111111", "name": "张经理"}', 45);

-- 插入第二条记录
INSERT INTO `lab` (`id`, `name`, `state`, `quantity`, `description`, `manager`, `enable_equipment`)
VALUES ('00000000000000000000000002', '902', 1, 50, '主要用于AI算法研究', NULL, 50);

-- 插入第三条记录
INSERT INTO `lab` (`id`, `name`, `state`, `quantity`, `description`, `manager`, `enable_equipment`)
VALUES ('00000000000000000000000003', '903', 1, 50, '专注于大数据处理相关实验', '{"id": "222222", "name": "李经理"}', 50);

-- 插入第四条记录
INSERT INTO `lab` (`id`, `name`, `state`, `quantity`, `description`, `manager`, `enable_equipment`)
VALUES ('00000000000000000000000004', '904', 1, 50, '用于物联网项目开发与测试', NULL, 49);

-- 插入第五条记录
INSERT INTO `lab` (`id`, `name`, `state`, `quantity`, `description`, `manager`, `enable_equipment`)
VALUES ('00000000000000000000000005', '905', 1, 130, '提供软件开发的环境', '{"id": "333333", "name": "王经理"}', 128);

-- 插入第六条记录
INSERT INTO `lab` (`id`, `name`, `state`, `quantity`, `description`, `manager`, `enable_equipment`)
VALUES ('00000000000000000000000006', '906', 1, 130, '专注于图像识别等视觉相关研究', NULL, 127);
