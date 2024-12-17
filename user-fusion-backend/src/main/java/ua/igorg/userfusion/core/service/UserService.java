package ua.igorg.userfusion.core.service;

import ua.userfusion.server.model.UserDto;

import java.util.List;

/**
 * @author igorg
 * @create 01.06.2024
 */
public interface UserService {

	List<UserDto> getUsers();
}
