package cn.smbms.dao.provider.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.smbms.bean.Provider;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.util.BaseDao;
import cn.smbms.util.PageUtil;

import com.mysql.jdbc.PreparedStatement;

public class ProviderDaoImpl implements ProviderDao {

	// 通过id得到一个供应商对象
	@Override
	public Provider getById(Connection connection, Serializable id)
			throws Exception {
		Provider provider = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		if (connection != null) {
			String sql = "select * from smbms_provider where id=?";
			Object[] params = { id };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				provider = new Provider();
				provider.setId(rs.getInt("id"));
				provider.setProCode(rs.getString("proCode"));
				provider.setProName(rs.getString("proName"));
				provider.setProContact(rs.getString("proContact"));
				provider.setProPhone(rs.getString("proPhone"));
				provider.setProAdderss(rs.getString("proAdderss"));
				provider.setProFax(rs.getString("proFax"));
				provider.setProDesc(rs.getString("proDesc"));
				provider.setCreationDate(rs.getTimestamp("creationDate"));
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return provider;
	}

	// 增加供应商
	@Override
	public boolean add(Connection connection, Provider provider)
			throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "insert smbms_provider"
					+ "(proCode,proName,proDesc,proContact,proPhone,proAdderss,proFax,createdBy,creationDate)"
					+ " values(?,?,?,?,?,?,?,?,?);";
			Object[] params = { provider.getProCode(), provider.getProName(),
					provider.getProDesc(), provider.getProContact(),
					provider.getProPhone(), provider.getProAdderss(),
					provider.getProFax(), provider.getCreatedBy(),
					provider.getCreationDate() };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	// 得到去重的供应商列表
	@Override
	public List<Provider> getProviderList(Connection connection)
			throws Exception {
		List<Provider> proList = new ArrayList<Provider>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		if (connection != null) {
			String sql = "SELECT * FROM  smbms_provider GROUP BY proName";

			rs = BaseDao.execute(connection, pstm, rs, sql);
			while (rs.next()) {
				Provider provider = new Provider();
				provider.setId(rs.getInt("id"));
				provider.setProCode(rs.getString("proCode"));
				provider.setProName(rs.getString("proName"));
				provider.setProContact(rs.getString("proContact"));
				provider.setProPhone(rs.getString("proPhone"));
				provider.setProFax(rs.getString("proFax"));
				provider.setCreationDate(rs.getTimestamp("creationDate"));
				proList.add(provider);

			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return proList;
	}

	// 删除供应商
	@Override
	public boolean deleteById(Connection connection, Serializable id)
			throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "delete from smbms_provider where id=?";
			Object[] params = { id };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	// 修改供应商
	@Override
	public boolean modify(Connection connection, Provider provider)
			throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "update smbms_provider set proCode=?,proName=?,proDesc=?,"
					+ "proContact=?,proPhone=?,proAdderss=?,proFax=?,modifyBy=?,modifyDate=? "
					+ "where id=?";
			Object[] params = { provider.getProCode(), provider.getProName(),
					provider.getProDesc(), provider.getProContact(),
					provider.getProPhone(), provider.getProAdderss(),
					provider.getProFax(), provider.getModifyBy(),
					provider.getModifyDate(), provider.getId() };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	// 得到分页后的供应商列表
	@Override
	public List<Provider> getPageList(Connection connection, String proName,
			PageUtil pageUtil) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Provider> proList = new ArrayList<Provider>();
		if (connection != null) {
			String sql = "select * from smbms_provider where proName like ? limit ?,?";
			Object[] params = { ("%" + proName + "%"),
					(pageUtil.getPageIndex() - 1) * pageUtil.getPageSize(),
					pageUtil.getPageSize() };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			while (rs.next()) {
				Provider provider = new Provider();
				provider.setId(rs.getInt("id"));
				provider.setProCode(rs.getString("proCode"));
				provider.setProName(rs.getString("proName"));
				provider.setProContact(rs.getString("proContact"));
				provider.setProPhone(rs.getString("proPhone"));
				provider.setProFax(rs.getString("proFax"));
				provider.setCreationDate(rs.getTimestamp("creationDate"));

				proList.add(provider);// 向集合中赋值
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return proList;
	}

	// 模糊查询得到供应商总数
	@Override
	public Integer getTotalCount(Connection connection, String proName)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer totalCounts = 0;
		if (connection != null) {
			String sql = "select  count(1)  from smbms_provider where proName like ?";
			Object[] params = { "%" + proName + "%" };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				totalCounts = rs.getInt(1);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return totalCounts;
	}

}
