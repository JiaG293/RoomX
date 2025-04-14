package com.roomx.infrastructure.security.oauth;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.repository.UserRepository;
import com.roomx.shared.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("roleEvaluator")
@RequiredArgsConstructor
public class RoleEvaluator {
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public boolean hasHigherRole(String targetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }

        UUID currentUserId = UUID.fromString(authentication.getName());
        UUID targetUserId = UUID.fromString(targetId);

        if (currentUserId.equals(targetUserId)) {
            return true;
        }

        int userLevel = userRepository.findById(currentUserId, true)
                .map(user -> user.getRoles().stream()
                        .mapToInt(Role::getLevel)
                        .max()
                        .orElse(0))
                .orElse(0);

        int targetLevel = userRepository.findById(targetUserId, true)
                .map(user -> user.getRoles().stream()
                        .mapToInt(Role::getLevel)
                        .max()
                        .orElse(0))
                .orElse(0);

        return userLevel > targetLevel;
    }

    @Transactional(readOnly = true)
    public boolean hasAnyRole(List<String> roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }

        return userRepository.findById(UUID.fromString(authentication.getName()), true)
                .map(user -> user.getRoles().stream()
                        .map(Role::getId)
                        .anyMatch(roleNames::contains))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }

        return userRepository.findById(UUID.fromString(authentication.getName()), true)
                .map(user -> user.getRoles().stream()
                        .map(Role::getId)
                        .anyMatch(roleId -> roleId.equals(roleName)))
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean hasAnyRoleType(String type) {
        List<String> roleNames = new ArrayList<>();
        switch (type){
            case "approve":
                roleNames.add(RoleType.ADMIN.toString());
                roleNames.add(RoleType.OWNER.toString());
                roleNames.add(RoleType.APPROVER.toString());
                break;
            case "manager":
                roleNames.add(RoleType.ADMIN.toString());
                roleNames.add(RoleType.APPROVER.toString());
                roleNames.add(RoleType.SUPPORTER.toString());
                break;
            case "user":
                roleNames.add(RoleType.ADMIN.toString());
                roleNames.add(RoleType.OWNER.toString());
                break;
            default:
                roleNames.add(RoleType.USER.name());
                break;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }

        return userRepository.findById(UUID.fromString(authentication.getName()), true)
                .map(user -> user.getRoles().stream()
                        .map(Role::getId)
                        .anyMatch(roleNames::contains))
                .orElse(false);
    }


}
