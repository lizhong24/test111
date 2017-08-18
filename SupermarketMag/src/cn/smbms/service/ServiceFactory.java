package cn.smbms.service;

import cn.smbms.service.user.impl.UserServiceImpl;

public class ServiceFactory {
	private static ServiceFactory factory;// Service层工厂类对象

	/**
	 * 静态代码块，加载类时即可获取ServiceFactory对象
	 */
	static {
		if (factory == null) {
			synchronized (ServiceFactory.class) {
				if (factory == null) {
					factory = new ServiceFactory();
				}
			}
		}
	}

	/**
	 * 私有化构造
	 */
	private ServiceFactory() {

	}

	/**
	 * 根据业务层发送的参数，获取相应的Service层访问数据库对象
	 * @param ServiceSign 对应Service层访问数据库对象的标记
	 * @return Service层访问数据库对象
	 */
	@SuppressWarnings("rawtypes")
	public static ConService getServiceImpl(String serviceImpl) {
		ConService service = null;
		switch (serviceImpl) {
		case "UserService":
			service = new UserServiceImpl();
			break;

		default:
			break;
		}

		return service;
	}
}
