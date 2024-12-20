package ua.igorg.userfusion.core.service;

import java.util.List;
import java.util.Optional;

import ua.userfusion.server.model.UserDto;

/** Created by igorg on 01.06.2024 */
public interface UserService {

  List<UserDto> getUsers();

	List<UserDto> getUsers(Optional<String> username, Optional<String> name, Optional<String> surname);
}
