package com.pibox.core.mapper;

import com.pibox.core.domain.dto.GroupDto;
import com.pibox.core.domain.model.Group;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {

    GroupDto toGroupDto(Group group);

    Group toGroup(GroupDto groupDto);
}
