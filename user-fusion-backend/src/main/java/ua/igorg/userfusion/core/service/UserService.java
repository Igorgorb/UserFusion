package ua.igorg.userfusion.core.service;

import ua.userfusion.server.model.UserDto;

import java.util.List;

/**
 * Created by igorg
 * on 01.06.2024
 */
public interface UserService {

	List<UserDto> getUsers();
}
