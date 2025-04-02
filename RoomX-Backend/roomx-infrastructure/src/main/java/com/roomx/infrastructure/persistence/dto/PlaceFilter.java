package com.roomx.infrastructure.persistence.dto;

import com.roomx.shared.base.BaseFilter;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PlaceFilter extends BaseFilter {
    private String placeType;
    private String status;
}
