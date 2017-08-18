package cn.smbms.service.user;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.smbms.bean.User;
import cn.smbms.service.user.impl.UserServiceImpl;

public class UserServiceTest {
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		userService = new UserServiceImpl();
	}

	@Test
	public void testAdd() {
		User user = new User();
		user.setUserCode("1111");
		user.setUserName("2222");
		boolean result = userService.add(user);
		// result = false;
		// 断言
		Assert.assertTrue("增加失败", result);
	}

	@Test
	public void testGetUserList() {
		List<User> userList = new ArrayList<User>();
		userList = userService.getUserList("null");
		Assert.assertEquals(11, userList.size());
	}
}
