package cn.bdqn.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseDao {

	/**
	 * 提取公共的属性
	 */
	protected Connection connection = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	/**
	 * 公共连接数据库的方法     JDBC  API
	 * public boolean getConnection() {
		try {
			// 加载驱动
			Class.forName(ConfigManager.getValue("jdbc.driverClass"));
			// 连接数据库
			connection = DriverManager.getConnection(
					ConfigManager.getValue("jdbc.url"),
					ConfigManager.getValue("jdbc.userName"),
					ConfigManager.getValue("jdbc.password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	 */
	public boolean getConnection() {
		// 初始化上下文信息 获取服务器
		try {
			Context context = new InitialContext();
			DataSource source = (DataSource) context
					.lookup("java:comp/env/jdbc/news");
			connection = source.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 释放资源
	 * 必须做非空判断
	 */
	public void closeConnection() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 提取所有类的增删改方法excuteUpdate
	 * @param sql
	 * @param params
	 * @return
	 */
	public int excuteUpdate(String sql, Object... params) {
		int rowNum = 0;// 影响的行数
		if (getConnection()) { // 如果有连接
			try {
				// 创建执行sql的对象
				ps = connection.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						ps.setObject(i + 1, params[i]);
					}
				}
				rowNum = ps.executeUpdate();// 没有参数的
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// 释放资源
				closeConnection();
			}
		}
		return rowNum;
	}

	public ResultSet excuteQuery(String sql, Object... params) {
		if (getConnection()) {// 如果有连接
			try {
				// 创建执行sql的对象
				ps = connection.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						ps.setObject(i + 1, params[i]);
					}
				}
				rs = ps.executeQuery();// 没有参数的
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
}
