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

public class UserServiceImpl implements UserService {

	private UserDao dao;

	public UserServiceImpl() {
		dao = (UserDao) DaoFactory.getDaoImpl("UserDao");
	}

	@Override
	public Integer getTotalCount() {
		Connection connection = null;
		int totalCounts = 0;
		try {
			connection = BaseDao.getConnection();
			totalCounts = dao.getTotalCount(connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return totalCounts;
	}

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

	@Override
	public List<User> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getList(PageUtil pageUtil) {
		Connection connection = null;
		List<User> userList = null;
		try {
			connection = BaseDao.getConnection();
			userList = dao.getList(connection, pageUtil);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return userList;
	}

	@Override
	public boolean add(User user) {
		boolean flag = false;
		Connection connection = null;
		// PreparedStatement pstm = null;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);// 开启JDBC事务
			int updateRows = dao.add(connection, user);
			// float a = 6 / 0;
			connection.commit();
			if (updateRows > 0) {
				flag = true;
				System.out.println("add success!");
			} else {
				System.out.println("add failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("rollback==============");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	@Override
	public boolean delete(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User login(String userCode, String userPassword) {
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

		// 匹配密码
		if (null != user) {
			if (!user.getUserPassword().equals(userPassword)) {
				user = null;
			}
		}
		return user;
	}

	@Override
	public List<User> getUserList(String queryUserName) {
		Connection connection = null;
		List<User> userList = null;
		System.out.println("queryUserName--->" + queryUserName);
		if (queryUserName == null) {
			queryUserName = "";
		}

		try {
			connection = BaseDao.getConnection();
			userList = dao.getUserList(connection, queryUserName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return userList;
	}

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

	@Override
	public boolean deleteUserById(Integer delId) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = dao.deleteUserById(connection, delId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	@Override
	public boolean modifyUser(User user) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = dao.modifyUser(connection, user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

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
