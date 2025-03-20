package com.roomx.shared.dto.user.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupCreateUserRequest {
    private String name;
    @Pattern(regexp = "SELF|PARTNER", message = "groupType chỉ có thể là SELF | PARTNER")
    private String groupType;
    private String groupCode;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> groupMembers;
}
