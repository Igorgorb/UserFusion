package ua.igorg.userfusion.api.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.igorg.userfusion.core.service.UserService;
import ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.api.UserResourceApi;
import ua.userfusion.specifications.spring_boot_openapi_generation.v1_0_0.server.model.User;

import java.util.List;

import static ua.igorg.userfusion.util.WebUtil.getFullRequestUri;

/**
 * @Author igorg
 * @create 31.05.2024
 */
@RestController
public class UserResource implements UserResourceApi {

	private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

	private final UserService userService;
	private final TypeConverter<ua.igorg.userfusion.core.domain.User, User> converter;

	public UserResource(
		final UserService userService,
		final TypeConverter<ua.igorg.userfusion.core.domain.User, User> converter
	) {
		this.userService = userService;
		this.converter = converter;
	}

	@Override
	public ResponseEntity<List<User>> getUsers() {
		LOG.debug(
			"Received GET request to get users list, request URI:[{}]",
			getFullRequestUri()
		);
		return ResponseEntity.ok(
			converter.convert(userService.getUsers()));
	}
}