package cn.smbms.dao.user;

import java.sql.Connection;
import java.util.List;

import cn.smbms.bean.User;
import cn.smbms.dao.ConDao;
import cn.smbms.util.PageUtil;

public interface UserDao extends ConDao<User> {

	/**
	 * 通过userCode获取User
	 * @param connection
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(Connection connection, String userCode)
			throws Exception;

	/**
	 * 修改当前用户密码
	 * @param connection
	 * @param id
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public boolean updatePwd(Connection connection, Integer id, String pwd)
			throws Exception;

	/**
	 * 根据分页对象中分页信息，从数据库模糊查询获取对象列表
	 * @param connection
	 * @param userName
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	List<User> getPageList(Connection connection, String userName,
			PageUtil pageUtil) throws Exception;

	/**
	 * 获取数据库数据总记录数
	 * @param connection
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	Integer getTotalCount(Connection connection, String userName)
			throws Exception;

}
