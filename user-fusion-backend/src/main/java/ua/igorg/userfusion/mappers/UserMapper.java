package ua.igorg.userfusion.mappers;

import org.mapstruct.Mapper;
import ua.igorg.userfusion.core.domain.User;
import ua.userfusion.server.model.UserDto;

import java.util.List;

/**
 * @author igorg
 * @create 17.12.2024
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);
}
