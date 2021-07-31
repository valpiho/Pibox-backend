package com.pibox.core.mapper;

import com.pibox.core.domain.dto.UserDto;
import com.pibox.core.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto userToUserDto(User user);
}
