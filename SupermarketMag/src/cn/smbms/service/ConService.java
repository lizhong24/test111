package cn.smbms.service;

import java.io.Serializable;
import java.util.List;

import cn.smbms.util.PageUtil;

/**
 * Service业务逻辑层公共接口
 * @param <T> 代表某个类型的占位符
 */

public interface ConService<T> {

	/**
	 * 获取数据库中对象数据的总记录数
	 * @return
	 */
	Integer getTotalCount();

	/**
	 * 通过ID获取携带信息的对象
	 * @param id
	 * @return
	 */
	T getById(Serializable id);

	/**
	 * 获取所有携带信息的对象列表
	 * @return
	 */
	List<T> getList();

	/**
	 * 通过分页对象获取携带信息的对象列表
	 * @param pageUtil 分页对象
	 * @return
	 */
	List<T> getList(PageUtil pageUtil);

	/**
	 * 将携带用户输入信息的对象，不包含id，传递给数据访问层并保存
	 * @param T   携带用户输入信息的对象
	 * @return
	 */
	boolean add(T t);

	/**
	 * 将携带id信息的对象，传递给数据访问层并执行删除该对象的信息
	 * @param t 携带id信息的对象
	 * @return
	 */
	boolean delete(T t);

	/**
	 * 将携带用户输入信息的对象，包含id，传递给数据访问层并通过id修改指定的对象信息
	 * @param t   携带用户输入信息的对象，包含id
	 * @return
	 */
	boolean update(T t);
}
