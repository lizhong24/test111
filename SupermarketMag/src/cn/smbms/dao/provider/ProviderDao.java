package cn.smbms.dao.provider;

import java.sql.Connection;
import java.util.List;

import cn.smbms.bean.Provider;
import cn.smbms.dao.ConDao;
import cn.smbms.util.PageUtil;

public interface ProviderDao extends ConDao<Provider> {

	/**
	 * 通过供应商名称获取供应商列表-模糊查询-providerList
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public List<Provider> getProviderList(Connection connection)
			throws Exception;

	/**
	 * 根据分页对象中分页信息，从数据库模糊查询获取对象列表
	 * @param connection
	 * @param proName
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	List<Provider> getPageList(Connection connection, String proName,
			PageUtil pageUtil) throws Exception;

	/**
	 * 获取数据库数据总记录数
	 * @param connection
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	Integer getTotalCount(Connection connection, String proName)
			throws Exception;
}
