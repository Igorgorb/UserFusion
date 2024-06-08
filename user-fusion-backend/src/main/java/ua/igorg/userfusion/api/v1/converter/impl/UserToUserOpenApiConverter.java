package ua.igorg.userfusion.api.v1.converter.impl;

import org.springframework.stereotype.Component;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.igorg.userfusion.core.domain.User;

/**
 * @Author igorg
 * @create 03.06.2024
 */
@Component
public class UserToUserOpenApiConverter
	implements TypeConverter<User, ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User> {
	@Override
	public Class<User> getSourceClass() {
		return User.class;
	}

	@Override
	public Class<ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User> getTargetClass() {
		return ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User.class;
	}

	@Override
	public ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User convert(final User source) {
		return ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User.builder()
			.id(source.getId())
			.username(source.getUsername())
			.name(source.getName())
			.surname(source.getSurname())
			.build();
	}
}
