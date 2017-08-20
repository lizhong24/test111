package cn.smbms.service.user.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.smbms.bean.User;
import cn.smbms.dao.DaoFactory;
import cn.smbms.dao.user.UserDao;
import cn.smbms.service.user.UserService;
import cn.smbms.util.BaseDao;
import cn.smbms.util.PageUtil;

/**
 * 用户的业务逻辑层
 */
public class UserServiceImpl implements UserService {

	private UserDao dao;

	public UserServiceImpl() {
		dao = (UserDao) DaoFactory.getDaoImpl("UserDao");
	}

	// 通过id得到当前用户对象
	@Override
	public User getById(Serializable id) {
		User user = null;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			user = dao.getById(connection, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return user;
	}

	// 增加用户
	@Override
	public boolean add(User user) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);// 开启JDBC事务
			flag = dao.add(connection, user);
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 用户登录的方法
	@Override
	public User login(String userCode) {
		Connection connection = null;
		User user = null;
		try {
			connection = BaseDao.getConnection();
			user = dao.getLoginUser(connection, userCode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}

		return user;
	}

	// 判断用户编码是否存在
	@Override
	public User selectUserCodeExist(String userCode) {
		Connection connection = null;
		User user = null;
		try {
			connection = BaseDao.getConnection();
			user = dao.getLoginUser(connection, userCode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return user;
	}

	// 通过id删除用户
	@Override
	public boolean deleteUserById(Integer delId) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = dao.deleteById(connection, delId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 修改用户
	@Override
	public boolean update(User user) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = dao.modify(connection, user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 修改密码
	@Override
	public boolean updatePwd(Integer id, String pwd) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);
			flag = dao.updatePwd(connection, id, pwd);
			if (flag) {
				connection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 得到分页后的用户列表
	@Override
	public List<User> getPageList(String userName, PageUtil pageUtil) {
		Connection connection = null;
		List<User> userList = null;
		try {
			if (userName == null) {
				userName = "";
			}
			connection = BaseDao.getConnection();
			userList = dao.getPageList(connection, userName, pageUtil);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return userList;
	}

	// 得到用户总数
	@Override
	public Integer getTotalCount(String userName) {
		Connection connection = null;
		int totalCounts = 0;
		try {
			if (userName == null) {
				userName = "";
			}
			connection = BaseDao.getConnection();
			totalCounts = dao.getTotalCount(connection, userName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return totalCounts;
	}

}
