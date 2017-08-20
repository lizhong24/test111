package cn.smbms.service.provider;

import java.util.List;

import cn.smbms.bean.Provider;
import cn.smbms.service.ConService;
import cn.smbms.util.PageUtil;

public interface ProviderService extends ConService<Provider> {
	/**
	 * 增加供应商
	 * @param provider
	 * @return
	 */
	public boolean add(Provider provider);

	/**
	 * 通过供应商名称获取供应商列表-模糊查询-providerList
	 * @param proName
	 * @return
	 */
	public List<Provider> getProviderList(String proName);

	/**
	 * 通过delId删除Provider
	 * @param delId
	 * @return
	 */
	public int deleteProviderById(String delId);

	/**
	 * 通过proId获取Provider
	 * @param id
	 * @return
	 */
	public Provider getProviderById(String id);

	/**
	 * 修改provider信息
	 * @param provider
	 * @return
	 */
	public boolean modifyProvider(Provider provider);

	/**
	 * 根据分页对象中分页信息，从数据库模糊查询获取对象列表
	 * @param proName
	 * @param pageUtil
	 * @return
	 * @throws Exception
	 */
	List<Provider> getPageList(String proName, PageUtil pageUtil);

	/**
	 * 根据供应商名称获取数据库数据总记录数
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	Integer getTotalCount(String proName);
}
