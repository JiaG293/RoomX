package com.roomx.infrastructure.persistence.repository.impl;

import com.roomx.domain.model.aggrerate.Group;
import com.roomx.domain.model.aggrerate.Place;
import com.roomx.domain.repository.GroupRepository;
import com.roomx.infrastructure.persistence.mapper.GroupEntityMapper;
import com.roomx.infrastructure.persistence.repository.jpa.JpaGroupEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GroupEntityRepository implements GroupRepository {
    private final JpaGroupEntityRepository jpaGroupEntityRepository;
    private final GroupEntityMapper groupEntityMapper;

    @Override
    public Optional<Group> findById(String id, String status) {
        return jpaGroupEntityRepository
                .findById(UUID.fromString(id))
                .map(groupEntityMapper::toDomain);
    }

    @Override
    public Group save(Group group) {
        var groupEntity = groupEntityMapper.toEntity(group);
        var savedGroupEntity = jpaGroupEntityRepository.save(groupEntity);
        return groupEntityMapper.toDomain(savedGroupEntity);
    }

    @Override
    public Optional<Group> findByGroupCodeAndStatus(String groupCode, String status) {
        return jpaGroupEntityRepository
                .findByGroupCodeAndStatus(groupCode, status)
                .map(groupEntityMapper::toDomain);
    }

    @Override
    public boolean checkGroupExistWithStatusAndGroupCode(String groupCode, String defaultString) {
        return false;
    }



}
