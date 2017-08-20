package cn.smbms.service.user;

import java.util.List;

import cn.smbms.bean.User;
import cn.smbms.service.ConService;
import cn.smbms.util.PageUtil;

public interface UserService extends ConService<User> {

	/**
	 * 用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	public User login(String userCode);

	/**
	 * 根据userCode查询出User
	 * @param userCode
	 * @return
	 */
	public User selectUserCodeExist(String userCode);

	/**
	 * 根据ID删除user
	 * @param delId
	 * @return
	 */
	public boolean deleteUserById(Integer delId);

	/**
	 * 根据userId修改密码
	 * @param id
	 * @param pwd
	 * @return
	 */
	public boolean updatePwd(Integer id, String pwd);

	/**
	 * 根据分页对象中分页信息，从数据库模糊查询获取对象列表
	 * @param userName
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	List<User> getPageList(String userName, PageUtil pageUtil);

	/**
	 * 根据用户名获取数据库数据总记录数
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	Integer getTotalCount(String userName);

}
