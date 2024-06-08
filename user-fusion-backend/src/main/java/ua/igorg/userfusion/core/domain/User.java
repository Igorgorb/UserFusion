package ua.igorg.userfusion.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author igorg
 * @create 31.05.2024
 */
@Data
@AllArgsConstructor
public class User {
	private String id;
	private String username;
	private String name;
	private String surname;
}
