package ua.igorg.userfusion.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.igorg.userfusion.config.DataSourceFactory;
import ua.igorg.userfusion.config.datasources.DataSourceProperties;

/**
 * @Author igorg
 * @create 07.06.2024
 */
@Disabled
@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImplTest")
class UserServiceImplTest {

	@Mock
	private DataSourceFactory dataSourceFactory;
	@Mock
	private DataSourceProperties dataSourceProperties;

	@InjectMocks
	private UserServiceImpl badgeAssignmentService;

	@BeforeEach
	public void setUp() {
//		user = UserFactory.createUserWithId(USER_ID);
//		badge = new Badge(0L, List.of(new BadgeLocalization("name", "description", BadgeCriteria.TASK_ONE)));
	}

	@Test
	void getUsersMS() throws Exception {
	}
}