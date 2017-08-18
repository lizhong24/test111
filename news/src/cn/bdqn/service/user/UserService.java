package cn.bdqn.service.user;

import cn.bdqn.bean.News_User;

public interface UserService {
	public News_User loginUser(String userName, String password);
}
