package com.roomx.infrastructure.persistence.dto;

import com.roomx.shared.base.BaseFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserFilter extends BaseFilter {
    private String userType;
    private String branchId;
    private String groupId;
    private Boolean enabled;
    private List<String> roles;
}
