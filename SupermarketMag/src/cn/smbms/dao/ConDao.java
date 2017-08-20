package cn.smbms.dao;

import java.io.Serializable;
import java.sql.Connection;

/**
 * 公共的接口类
 *
 * @param <T>
 */
public interface ConDao<T> {

	/**
	 * 根据id从数据库获取对应的数据信息，封装成对象
	 * @param id
	 * @return
	 */
	T getById(Connection connection, Serializable id) throws Exception;

	/**
	 * 根据对象携带的信息（没有id），向数据库添加信息
	 * @param t
	 * @return
	 */
	boolean add(Connection connection, T t) throws Exception;

	/** 修改信息
	* @param connection
	* @param t
	* @return
	* @throws Exception
	*/
	boolean modify(Connection connection, T t) throws Exception;

	/**
	 * 通过id删除
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteById(Connection connection, Serializable id)
			throws Exception;

}
