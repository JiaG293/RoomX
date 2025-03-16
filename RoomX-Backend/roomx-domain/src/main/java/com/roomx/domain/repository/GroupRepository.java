package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Group;


import java.util.Optional;

public interface GroupRepository {
    Optional<Group> findById(String id);

    Group save(Group group);

}
