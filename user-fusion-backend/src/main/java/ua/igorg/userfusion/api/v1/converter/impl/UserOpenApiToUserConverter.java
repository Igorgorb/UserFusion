package ua.igorg.userfusion.api.v1.converter.impl;

import org.springframework.stereotype.Component;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User;

/**
 * @Author igorg
 * @create 03.06.2024
 */
@Component
public class UserOpenApiToUserConverter implements TypeConverter<User, ua.igorg.userfusion.core.domain.User> {
	@Override
	public Class<User> getSourceClass() {
		return User.class;
	}

	@Override
	public Class<ua.igorg.userfusion.core.domain.User> getTargetClass() {
		return ua.igorg.userfusion.core.domain.User.class;
	}

	@Override
	public ua.igorg.userfusion.core.domain.User convert(final User source) {
		return new ua.igorg.userfusion.core.domain.User(
			source.getId(),
			source.getUsername(),
			source.getName(),
			source.getSurname()
		);
	}
}
