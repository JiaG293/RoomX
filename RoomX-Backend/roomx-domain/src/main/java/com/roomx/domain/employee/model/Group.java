package com.roomx.domain.employee.model;

import com.roomx.domain.employee.enums.GroupScopeType;
import com.roomx.domain.employee.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private String id;
    private String name; // Tên nhóm
    private Set<User> users = new HashSet<>(); // Danh sách thành viên nhóm
    private GroupScopeType groupScopeType; // Phạm vi truy cập
    private GroupType groupType; // Loại nhóm (Phòng ban, Cá nhân, Chi nhánh)


}
