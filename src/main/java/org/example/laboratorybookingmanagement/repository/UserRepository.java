package org.example.laboratorybookingmanagement.repository;

import org.example.laboratorybookingmanagement.dox.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);

}
