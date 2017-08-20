package cn.smbms.dao.user.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.smbms.bean.User;
import cn.smbms.dao.user.UserDao;
import cn.smbms.util.BaseDao;
import cn.smbms.util.PageUtil;
import cn.smbms.util.ResultSetUtil;

import com.mysql.jdbc.PreparedStatement;

public class UserDaoImpl implements UserDao {

	@Override
	public Integer getTotalCount(Connection connection) throws Exception {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer totalCounts = 0;
		if (connection != null) {
			String sql = "select  count(1)  from smbms_user";
			rs = BaseDao.execute(connection, pstm, rs, sql);
			if (rs.next()) {
				totalCounts = rs.getInt(1);
			}
			BaseDao.closeResource(null, pstm, rs);
		}

		return totalCounts;
	}

	@Override
	public User getById(Connection connection, Serializable id)
			throws Exception {
		User user = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		if (connection != null) {
			String sql = "select * from smbms_user where id=?";
			Object[] params = { id };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUserCode(rs.getString("userCode"));
				user.setUserName(rs.getString("userName"));
				user.setUserPassword(rs.getString("userPassword"));
				user.setGender(rs.getInt("gender"));
				user.setBirthday(rs.getDate("birthday"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setUserType(rs.getInt("userType"));
				user.setCreateBy(rs.getInt("createBy"));
				user.setCreationDate(rs.getTimestamp("creationDate"));
				user.setModifyBy(rs.getInt("modifyBy"));
				user.setModifyDate(rs.getTimestamp("modifyDate"));
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return user;
	}

	@Override
	public List<User> getList(Connection connection) throws Exception {
		return null;
	}

	@Override
	public List<User> getList(Connection connection, PageUtil pageUtil)
			throws Exception {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<User> userList = null;
		if (connection != null) {
			String sql = "select * from smbms_user limit ?,?";
			Object[] params = {
					(pageUtil.getPageIndex() - 1) * pageUtil.getPageSize(),
					pageUtil.getPageSize() };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			try {
				userList = ResultSetUtil.eachResultSet(rs, User.class);
			} finally {
				BaseDao.closeResource(null, pstm, rs);
			}
		}
		return userList;
	}

	@Override
	public int add(Connection connection, User user) throws Exception {
		PreparedStatement pstm = null;
		int updateRows = 0;
		if (null != connection) {
			String sql = "insert into smbms_user(userCode,userName,userPassword,"
					+ "userType,gender,birthday,phone,address,creationDate,createBy) values(?,?,?,?,?,?,?,?,?,?)";
			Object[] params = { user.getUserCode(), user.getUserName(),
					user.getUserPassword(), user.getUserType(),
					user.getGender(), user.getBirthday(), user.getPhone(),
					user.getAddress(), user.getCreationDate(),
					user.getCreateBy() };
			updateRows = BaseDao.execute(connection, pstm, sql, params);
			BaseDao.closeResource(null, pstm, null);
		}

		return updateRows;
	}

	@Override
	public int delete(Connection connection, User user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Connection connection, User user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getLoginUser(Connection connection, String userCode)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User user = null;
		if (null != connection) {// 判断连接是否存在，严谨一些
			String sql = "select * from smbms_user where userCode=?";
			Object[] params = { userCode };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				user = new User();// 查到数据才new对象
				user.setId(rs.getInt("id"));
				user.setUserCode(rs.getString("userCode"));
				user.setUserName(rs.getString("userName"));
				user.setUserPassword(rs.getString("userPassword"));
				user.setGender(rs.getInt("gender"));
				user.setBirthday(rs.getDate("birthday"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setUserType(rs.getInt("userType"));
				user.setCreateBy(rs.getInt("createBy"));
				user.setCreationDate(rs.getTimestamp("creationDate"));
				user.setModifyBy(rs.getInt("modifyBy"));
				user.setModifyDate(rs.getTimestamp("modifyDate"));
			}
			BaseDao.closeResource(null, pstm, rs);
		}

		return user;
	}

	@Override
	public List<User> getUserList(Connection connection, String userName)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		if (connection != null) {
			String sql = "select * from smbms_user where userName like ?";
			Object[] params = { "%" + userName + "%" };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			while (rs.next()) {
				User _user = new User();
				_user.setId(rs.getInt("id"));
				_user.setUserCode(rs.getString("userCode"));
				_user.setUserName(rs.getString("userName"));
				_user.setGender(rs.getInt("gender"));
				_user.setBirthday(rs.getDate("birthday"));
				_user.setPhone(rs.getString("phone"));
				_user.setUserType(rs.getInt("userType"));

				userList.add(_user);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return userList;
	}

	@Override
	public boolean deleteUserById(Connection connection, Integer delId)
			throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "delete from smbms_user where id=?";
			Object[] params = { delId };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	@Override
	public boolean modifyUser(Connection connection, User user)
			throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "update smbms_user set "
					+ "userName=?,gender=?,birthday=?,phone=?,address=?,userType=?,"
					+ "modifyBy=?,modifyDate=? where id=?";
			Object[] params = { user.getUserName(), user.getGender(),
					user.getBirthday(), user.getPhone(), user.getAddress(),
					user.getUserType(), user.getModifyBy(),
					user.getModifyDate(), user.getId() };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	@Override
	public boolean updatePwd(Connection connection, Integer id, String pwd)
			throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "update smbms_user set userPassword =? where id=?";
			Object[] params = { pwd, id };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	@Override
	public List<User> getPageList(Connection connection, String userName,
			PageUtil pageUtil) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		if (connection != null) {
			String sql = "select * from smbms_user where userName like ? limit ?,?";
			Object[] params = { ("%" + userName + "%"),
					(pageUtil.getPageIndex() - 1) * pageUtil.getPageSize(),
					pageUtil.getPageSize() };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			while (rs.next()) {
				User _user = new User();
				_user.setId(rs.getInt("id"));
				_user.setUserCode(rs.getString("userCode"));
				_user.setUserName(rs.getString("userName"));
				_user.setGender(rs.getInt("gender"));
				_user.setBirthday(rs.getDate("birthday"));
				_user.setPhone(rs.getString("phone"));
				_user.setUserType(rs.getInt("userType"));
				userList.add(_user);// 向集合中赋值
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return userList;
	}

	@Override
	public Integer getTotalCount(Connection connection, String userName)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer totalCounts = 0;
		if (connection != null) {
			String sql = "select  count(1)  from smbms_user where userName like ?";
			Object[] params = { "%" + userName + "%" };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				totalCounts = rs.getInt(1);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return totalCounts;
	}

}
