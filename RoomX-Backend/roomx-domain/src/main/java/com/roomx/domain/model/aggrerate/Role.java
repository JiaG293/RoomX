package com.roomx.domain.model.aggrerate;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Role {
    private String id;
    private String description;
    private int level;
}
