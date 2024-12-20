package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);

}
