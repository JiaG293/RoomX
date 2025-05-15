package com.roomx.infrastructure.persistence.model.projection;

import java.util.List;
import java.util.UUID;

public interface UserProjection {
    UUID getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getUserName();
    Boolean getGender();
    String getPhoneNumber();
    Boolean getEnabled();
    String getUserType();
    String getUserCode();
    String getAvatarImage();
    String getStatus();


    UUID getGroupId();
    String getGroupName();
    String getGroupType();

    UUID getBranchId();
    String getBranchName();

    List<String> getArrayRoles();

}
