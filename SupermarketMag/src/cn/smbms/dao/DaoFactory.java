package cn.smbms.dao;

import cn.smbms.dao.bill.impl.BillDaoImpl;
import cn.smbms.dao.provider.impl.ProviderDaoImpl;
import cn.smbms.dao.user.impl.UserDaoImpl;

/**
 * Dao层工厂类，根据业务层传入的参数，获取要使用的Dao层对象
 */
public class DaoFactory {
	private static DaoFactory factory; // Dao层工厂类对象

	/**
	 * 静态代码块，加载类时即可获取DaoFactory对象
	 */
	static {
		if (factory == null) { // 双重所，处理高并发
			synchronized (DaoFactory.class) {
				if (factory == null)
					factory = new DaoFactory();
			}
		}
	}

	/**
	 * 私有化构造
	 */
	private DaoFactory() {

	}

	/**
	 * 根据业务层发送的参数，获取相应的Dao层访问数据库对象
	 * @param daoSign 对应Dao层访问数据库对象的标记
	 * @return Dao层访问数据库对象
	 */
	@SuppressWarnings("rawtypes")
	public static ConDao getDaoImpl(String daoImpl) {
		ConDao dao = null; // ConDao类型的Dao层访问数据库对象
		switch (daoImpl) {
		case "UserDao":
			dao = new UserDaoImpl();
			break;
		case "BillDao":
			dao = new BillDaoImpl();
			break;
		case "ProviderDao":
			dao = new ProviderDaoImpl();
			break;
		default:
			break;
		}
		return dao;
	}

}
