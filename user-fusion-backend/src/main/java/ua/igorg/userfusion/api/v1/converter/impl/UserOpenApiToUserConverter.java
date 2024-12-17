package ua.igorg.userfusion.api.v1.converter.impl;

import org.springframework.stereotype.Component;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.userfusion.server.model.UserDto;
import ua.igorg.userfusion.core.domain.User;

/**
 * @Author igorg
 * @create 03.06.2024
 */
@Component
public class UserOpenApiToUserConverter implements TypeConverter<UserDto, User> {
	@Override
	public Class<UserDto> getSourceClass() {
		return UserDto.class;
	}

	@Override
	public Class<ua.igorg.userfusion.core.domain.User> getTargetClass() {
		return ua.igorg.userfusion.core.domain.User.class;
	}

	@Override
	public User convert(final UserDto source) {
		return new User(
			source.getId(),
			source.getUsername(),
			source.getName(),
			source.getSurname()
		);
	}
}
