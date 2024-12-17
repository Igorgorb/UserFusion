package ua.igorg.userfusion.api.v1.converter.impl;

import org.springframework.stereotype.Component;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.igorg.userfusion.core.domain.User;
import ua.userfusion.server.model.UserDto;

/**
 * @Author igorg
 * @create 03.06.2024
 */
@Component
public class UserToUserOpenApiConverter
	implements TypeConverter<User, UserDto> {
	@Override
	public Class<User> getSourceClass() {
		return User.class;
	}

	@Override
	public Class<UserDto> getTargetClass() {
		return UserDto.class;
	}

	@Override
	public UserDto convert(final User source) {
		return UserDto.builder()
			.id(source.getId())
			.username(source.getUsername())
			.name(source.getName())
			.surname(source.getSurname())
			.build();
	}
}
