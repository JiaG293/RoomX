package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.vo.UserRoleId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository {
    Optional<User> findUserWithRole();
    List<Role> findRolesByUserId(UUID userId);
}

