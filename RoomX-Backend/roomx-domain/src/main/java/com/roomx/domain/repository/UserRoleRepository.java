package com.roomx.domain.repository;

import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.vo.UserRoleId;

import java.util.Optional;

public interface UserRoleRepository {
    Optional<User> findUserWithRole();

}

