package ua.igorg.userfusion.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.igorg.userfusion.core.service.UserService;
import ua.userfusion.server.api.UserResourceApi;
import ua.userfusion.server.model.UserDto;

import java.util.List;

import static ua.igorg.userfusion.util.WebUtil.getFullRequestUri;

/**
 * Created by igorg
 * on 31.05.2024
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserResource implements UserResourceApi {

    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        log.debug(
                "Received GET request to get users list, request URI:[{}]",
                getFullRequestUri()
        );
        return ResponseEntity.ok(userService.getUsers());
    }
}