package cn.smbms.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件的管理（单例类）
 */
public class ConfigManager {
	// 创建静态的自身对象，饿汉
	private static ConfigManager configManager;
	// 创建Properties对象 读取jdbc.properties文件
	private static Properties properties;

	// 私有化构造
	private ConfigManager() {
		// 加载配置文件
		String path = "jdbc.properties";
		properties = new Properties();
		// 创建输入流对象
		InputStream stream = ConfigManager.class.getClassLoader()
				.getResourceAsStream(path);
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 静态代码块，类一加载即可执行，获取配置文件管理对象
	static {
		if (configManager == null) {// 双重锁，处理高并发访问
			synchronized (ConfigManager.class) {
				if (configManager == null) {
					configManager = new ConfigManager();
				}
			}
		}

	}

	// properties文件是键值对的形式！ 我们提供key目的获取value
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
}
