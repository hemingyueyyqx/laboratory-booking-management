package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.Lab;
import org.example.laboratorybookingmanagement.dox.News;
import org.example.laboratorybookingmanagement.dto.EnableEquipmentCount;
import org.example.laboratorybookingmanagement.dto.LabCountDTO;
import org.example.laboratorybookingmanagement.dto.LabDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends ListCrudRepository<Lab,String> {
    //基于老师id，课程id，询状态可用，人数可用教室
    @Query("""
SELECT l.*
FROM lab l
join  course c on l.quantity >= c.quantity
WHERE c.teacher_id =:teacherId and c.id = :courseId and l.state =1;
""")
    List<Lab> findGoodLabs(String teacherId, String courseId);
    //基于老师id，课程id，查询状态可用，人数不够教室
    @Query("""
SELECT l.*
FROM lab l
join  course c on l.quantity < c.quantity
WHERE c.teacher_id =:teacherId and c.id = :courseId and l.state =1;
""")
    List<Lab> findBadLabs(String teacherId, String courseId);
    @Query("""
select * from lab l where l.manager ->> '$.id' =:id and l.state=1;
""")
    List<Lab> findLabs(String id);
    @Query("""
        SELECT state, count(state) as quantity from lab group by state
""")
    List<LabCountDTO> countLabByState();

    @Query("""
        select name,enable_equipment as enable_quantity,(quantity-enable_equipment) as unable_quantity from lab
""")
    List<EnableEquipmentCount> countEnableEquipment();

    @Query("""
            select id,name,state,quantity,description,manager from lab;
""")
    List<LabDTO> findAllLabs();
}
