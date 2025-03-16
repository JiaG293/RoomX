package com.roomx.infrastructure.multitenancy.security.oauth;

import com.roomx.domain.model.aggrerate.Role;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.enums.RoleType;
import com.roomx.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component("roleEvaluator")
@RequiredArgsConstructor
public class RoleEvaluator {
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public boolean hasHigherRole(String targetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false; // Không có user nào đăng nhập
        }
        int userLevel = userRepository.findById(UUID.fromString(authentication.getName()))
                .map(user -> user.getRoles().stream()
                        .mapToInt(Role::getLevel)
                        .max()
                        .orElse(0))
                .orElse(0);
        int targetLevel = userRepository.findById(UUID.fromString(targetId))
                .map(user -> user.getRoles().stream().mapToInt(Role::getLevel).max().orElse(0))
                .orElse(0);

        return userLevel > targetLevel;
    }

    @Transactional(readOnly = true)
    public boolean hasAnyRole(List<String> roleNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }

        return userRepository.findById(UUID.fromString(authentication.getName()))
                .map(user -> user.getRoles().stream()
                        .map(Role::getId)
                        .anyMatch(roleNames::contains))
                .orElse(false);
    }
}
