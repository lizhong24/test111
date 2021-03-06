package cn.smbms.service.provider.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.smbms.bean.Provider;
import cn.smbms.dao.DaoFactory;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.util.BaseDao;
import cn.smbms.util.PageUtil;

/**
 * 供应商的业务逻辑层
 */
public class ProviderServiceImpl implements ProviderService {

	private ProviderDao providerDao;
	private BillDao billDao;

	public ProviderServiceImpl() {
		providerDao = (ProviderDao) DaoFactory.getDaoImpl("ProviderDao");
		billDao = (BillDao) DaoFactory.getDaoImpl("BillDao");
	}

	// 通过id得到供应商对象
	@Override
	public Provider getById(Serializable id) {
		Connection connection = null;
		Provider provider = null;
		try {
			connection = BaseDao.getConnection();
			provider = providerDao.getById(connection, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return provider;
	}

	// 增加供应商
	@Override
	public boolean add(Provider provider) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);// 开启JDBC事务
			flag = providerDao.add(connection, provider);
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			// 在service层进行connection连接的关闭
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 得到去重的供应商列表
	@Override
	public List<Provider> getProviderList(String proName) {
		Connection connection = null;
		List<Provider> providerList = null;
		try {
			connection = BaseDao.getConnection();
			providerList = providerDao.getProviderList(connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return providerList;
	}

	/**
	 * 业务：根据ID删除供应商标的数据之前，需要先去订单表里面进行查询操作
	 * 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除
	 * 返回值billCount
	 * 1> billCount == 0   删除---1 成功 （0）2 不成功 （-1）
	 * 2> billCount > 0     不能删除  查询成功（0） 查询不成功  （-1）
	 * 
	 * ---判断
	 * 如果billCount = -1 失败
	 * 若billCount >= 0 成功
	 */
	@Override
	public Integer deleteProviderById(String delId) {
		Connection connection = null;
		int billCount = -1;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);
			billCount = billDao.getBillCountByProId(connection, delId);
			if (billCount == 0) {
				providerDao.deleteById(connection, delId);
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			billCount = -1;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return billCount;
	}

	// 修改供应商
	@Override
	public boolean update(Provider provider) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = providerDao.modify(connection, provider);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 根据供应商名称查询得到分页后的列表
	@Override
	public List<Provider> getPageList(String proName, PageUtil pageUtil) {
		Connection connection = null;
		List<Provider> proList = null;
		try {
			if (proName == null) {
				proName = "";
			}
			connection = BaseDao.getConnection();
			proList = providerDao.getPageList(connection, proName, pageUtil);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return proList;
	}

	// 根据供应商名称查询得到总记录数
	@Override
	public Integer getTotalCount(String proName) {
		Connection connection = null;
		int totalCounts = 0;
		try {
			if (proName == null) {
				proName = "";
			}
			connection = BaseDao.getConnection();
			totalCounts = providerDao.getTotalCount(connection, proName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return totalCounts;
	}

}
