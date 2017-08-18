package cn.smbms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 操作数据库的基类--静态类(非单例，因为每个线程都需要单独的connection，而不是大家用一个)
 * @author asus'
 *
 */
public class BaseDao {

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		String driver = ConfigManager.getValue("driver");
		String url = ConfigManager.getValue("url");
		String user = ConfigManager.getValue("user");
		String password = ConfigManager.getValue("password");
		Connection connection = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 查询操作
	 * @param connection
	 * @param pstm
	 * @param rs
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static ResultSet execute(Connection connection,
			PreparedStatement pstm, ResultSet rs, String sql, Object... params)
			throws Exception {
		pstm = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			pstm.setObject(i + 1, params[i]);
		}
		rs = pstm.executeQuery();
		return rs;
	}

	/**
	 * 更新操作
	 * @param connection
	 * @param pstm
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static int execute(Connection connection, PreparedStatement pstm,
			String sql, Object... params) throws Exception {
		int updateRows = 0;
		pstm = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			pstm.setObject(i + 1, params[i]);
		}
		updateRows = pstm.executeUpdate();
		return updateRows;
	}

	/**
	 * 释放资源
	 * @param connection
	 * @param pstm
	 * @param rs
	 * @return
	 */
	public static boolean closeResource(Connection connection,
			PreparedStatement pstm, ResultSet rs) {
		boolean flag = true;
		if (rs != null) {
			try {
				rs.close();
				rs = null;// GC回收
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			}
		}
		if (pstm != null) {
			try {
				pstm.close();
				pstm = null;// GC回收
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			}
		}
		if (connection != null) {
			try {
				connection.close();
				connection = null;// GC回收
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
			}
		}

		return flag;
	}
}
