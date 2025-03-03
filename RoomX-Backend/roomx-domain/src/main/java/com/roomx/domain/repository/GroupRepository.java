package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Optional<Group> findById(String id);

    Optional<Group> findByName(String name);

    Optional<Group> findByEmail(String email);

    List<Group> findAll();

    void save(Group group1);

    void delete(String id);
}
