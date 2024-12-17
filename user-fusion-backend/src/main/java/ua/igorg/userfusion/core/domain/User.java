package ua.igorg.userfusion.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author igorg
 * @create 31.05.2024
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String username;
    private String name;
    private String surname;
}
