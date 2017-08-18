package cn.smbms.dao;

import java.sql.Connection;
import java.util.List;

import cn.smbms.util.PageUtil;

public interface ConDao<T> {

	/**
	 * 获取数据库数据总记录数
	 * @return
	 */
	Integer getTotalCount(Connection connection) throws Exception;

	/**
	 * 根据id从数据库获取对应的数据信息，封装成对象
	 * @param id
	 * @return
	 */
	T getById(Connection connection, java.io.Serializable id) throws Exception;

	/**
	 * 从数据库获取对象列表
	 * @return
	 */
	List<T> getList(Connection connection) throws Exception;

	/**
	 * 根据分页对象中分页信息，从数据库获取对象列表
	 * @param pageUtil
	 * @return
	 */
	List<T> getList(Connection connection, PageUtil pageUtil) throws Exception;

	/**
	 * 根据对象携带的信息（没有id），向数据库添加信息
	 * @param t
	 * @return
	 */
	int add(Connection connection, T t) throws Exception;

	/**
	 * 根据对象携带的id信息，从数据库删除对应信息
	 * @param t
	 * @return
	 */
	int delete(Connection connection, T t) throws Exception;

	/**
	 * 根据对象携带的信息和id信息，在数据库修改信息
	 * @param t
	 * @return
	 */
	int update(Connection connection, T t) throws Exception;

}
