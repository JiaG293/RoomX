package com.roomx.application.service.employee;

import com.roomx.application.dto.request.UserCreateRequest;
import com.roomx.application.dto.request.UserQueryFilterRequest;
import com.roomx.application.dto.response.UserCreateResponse;
import com.roomx.application.dto.response.UserInfoReponse;
import com.roomx.application.dto.response.UserPageResponse;
import com.roomx.application.dto.response.UserResponse;

public interface UserApplicationService{
    UserInfoReponse getUserInfo();

    UserPageResponse getListUserPages(UserQueryFilterRequest filterRequest, int page, int size, String sortBy, String direction);

    UserCreateResponse createUser(UserCreateRequest request);

    String getTenant();
    UserResponse getUserDetail(String data);
}
