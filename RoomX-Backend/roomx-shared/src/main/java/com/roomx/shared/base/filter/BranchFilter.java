package com.roomx.shared.base.filter;

import com.roomx.shared.base.BaseFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BranchFilter extends BaseFilter {
    private Instant fromDate;
    private Instant toDate;
    private String status;

}
