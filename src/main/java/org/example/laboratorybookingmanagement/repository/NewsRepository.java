package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.News;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends ListCrudRepository<News,String> {
    //基于作者查公告

    @Query("""
        select * from news where author =:author;
""")
    List<News> findByAuthor(String author);

}
