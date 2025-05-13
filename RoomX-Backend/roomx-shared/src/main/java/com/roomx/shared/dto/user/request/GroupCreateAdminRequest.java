package com.roomx.shared.dto.user.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupCreateAdminRequest {
    private String name;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String branchId;
    @Pattern(regexp = "DEPARTMENT|PARTNER|SELF", message = "groupType chỉ có thể là DEPARTMENT | PARTNER | SELF")
    private String groupType;
    private String groupCode;
    private List<String> groupMembers;
}
