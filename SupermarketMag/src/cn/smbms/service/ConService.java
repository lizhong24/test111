package cn.smbms.service;

import java.io.Serializable;

/**
 * Service业务逻辑层公共接口
 * @param <T> 代表某个类型的占位符
 */

public interface ConService<T> {

	/**
	 * 通过ID获取携带信息的对象
	 * @param id
	 * @return
	 */
	T getById(Serializable id);

	/**
	 * 将携带用户输入信息的对象，不包含id，传递给数据访问层并保存
	 * @param T   携带用户输入信息的对象
	 * @return
	 */
	boolean add(T t);

	/**
	 * 将携带用户输入信息的对象，包含id，传递给数据访问层并通过id修改指定的对象信息
	 * @param t   携带用户输入信息的对象，包含id
	 * @return
	 */
	boolean update(T t);
}
