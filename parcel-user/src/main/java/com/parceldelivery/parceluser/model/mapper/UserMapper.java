package com.parceldelivery.parceluser.model.mapper;

import com.parceldelivery.parceluser.model.entity.User;
import com.parceldelivery.parceluser.model.request.UserCourierCreateRequest;
import com.parceldelivery.parceluser.model.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToModel(UserCourierCreateRequest orderRequest);

    User convertUserToUserCreateRequest(UserCreateRequest userCreateRequest);

}
