package com.roomx.domain.employee.repository;

import com.roomx.domain.employee.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Optional<Group> findById(String id);

    Optional<Group> findByName(String name);

    Optional<Group> findByEmail(String email);

    List<Group> findAll();

    void save(Group group);

    void delete(String id);
}
