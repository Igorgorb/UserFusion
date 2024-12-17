package ua.igorg.userfusion.api.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.igorg.userfusion.api.v1.converter.TypeConverter;
import ua.igorg.userfusion.core.domain.User;
import ua.igorg.userfusion.core.service.UserService;
import ua.userfusion.server.api.UserResourceApi;
import ua.userfusion.server.model.UserDto;

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
	private final TypeConverter<User, UserDto> converter;

	public UserResource(
		final UserService userService,
		final TypeConverter<User, UserDto> converter
	) {
		this.userService = userService;
		this.converter = converter;
	}

	@Override
	public ResponseEntity<List<UserDto>> getUsers() {
		LOG.debug(
			"Received GET request to get users list, request URI:[{}]",
			getFullRequestUri()
		);
		return ResponseEntity.ok(
			converter.convert(userService.getUsers()));
	}
}