package com.roomx.infrastructure.persistence.dto;

import com.roomx.shared.base.BaseFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GroupFilter extends BaseFilter {
    private String groupType;
    private String status;
    private String branchId;
    private String userId;
    private Boolean isAdmin;
    private Boolean viewAsUser;
}
