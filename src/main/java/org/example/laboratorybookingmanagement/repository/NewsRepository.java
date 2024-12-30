package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.News;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends ListCrudRepository<News,String> {

}
