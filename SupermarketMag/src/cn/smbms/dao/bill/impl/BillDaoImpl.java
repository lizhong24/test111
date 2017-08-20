package cn.smbms.dao.bill.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.smbms.bean.Bill;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.util.BaseDao;
import cn.smbms.util.PageUtil;

import com.mysql.jdbc.StringUtils;

public class BillDaoImpl implements BillDao {

	@Override
	public Integer getTotalCount(Connection connection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// 通过id得到账单对象
	@Override
	public Bill getById(Connection connection, Serializable id)
			throws Exception {
		Bill bill = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		if (connection != null) {
			String sql = "SELECT b.*,p.proName AS providerName FROM smbms_bill b, smbms_provider p WHERE b.providerId = p.id and b.id=?";
			Object[] params = { id };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				bill = new Bill();
				bill.setId(rs.getInt("id"));
				bill.setBillCode(rs.getString("billCode"));
				bill.setProductName(rs.getString("productName"));
				bill.setProductDesc(rs.getString("productDesc"));
				bill.setProductUnit(rs.getString("productUnit"));
				bill.setProductCount(rs.getBigDecimal("productCount"));
				bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
				bill.setIsPayment(rs.getInt("isPayment"));
				bill.setProviderId(rs.getInt("providerId"));
				bill.setProviderName(rs.getString("providerName"));
				bill.setCreationDate(rs.getTimestamp("creationDate"));
				bill.setCreatedBy(rs.getInt("createdBy"));
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return bill;
	}

	@Override
	public List<Bill> getList(Connection connection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bill> getList(Connection connection, PageUtil pageUtil)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(Connection connection, Bill t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Connection connection, Bill t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Connection connection, Bill t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	// 根据供应商Id，查询订单数量
	@Override
	public int getBillCountByProId(Connection connection, String proId)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int billCount = 0;
		if (connection != null) {
			String sql = "select count(1) from smbms_bill where providerId=?";
			Object[] params = { proId };
			rs = BaseDao.execute(connection, pstm, rs, sql, params);
			if (rs.next()) {
				billCount = rs.getInt("count(1)");
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return billCount;
	}

	@Override
	public Bill getBillById(Connection connection, String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// 增加账单
	@Override
	public boolean addBill(Connection connection, Bill bill) throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "insert into smbms_bill (billCode,productName,productDesc,productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			Object[] params = { bill.getBillCode(), bill.getProductName(),
					bill.getProductDesc(), bill.getProductUnit(),
					bill.getProductCount(), bill.getTotalPrice(),
					bill.getIsPayment(), bill.getProviderId(),
					bill.getCreatedBy(), bill.getCreationDate() };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	// 修改账单
	@Override
	public boolean modifyBill(Connection connection, Bill bill)
			throws Exception {
		boolean flag = false;
		PreparedStatement preparedStatement = null;
		if (connection != null) {
			String sql = "update smbms_bill set productName=?,productDesc=?,productUnit=?,productCount=?,totalPrice=?,"
					+ "isPayment=?,providerId=?,modifyBy=?,modifyDate=? where id = ? ";
			Object[] params = { bill.getProductName(), bill.getProductDesc(),
					bill.getProductUnit(), bill.getProductCount(),
					bill.getTotalPrice(), bill.getIsPayment(),
					bill.getProviderId(), bill.getModifyBy(),
					bill.getModifyDate(), bill.getId() };
			if (BaseDao.execute(connection, preparedStatement, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, preparedStatement, null);
		}
		return flag;
	}

	// 删除账单
	@Override
	public boolean delBill(Connection connection, String id) throws Exception {
		boolean flag = false;
		PreparedStatement pstm = null;
		if (connection != null) {
			String sql = "delete from smbms_bill where id=?";
			Object[] params = { id };
			if (BaseDao.execute(connection, pstm, sql, params) > 0) {
				flag = true;
			}
			BaseDao.closeResource(null, pstm, null);
		}
		return flag;
	}

	// 分页获取pageBillList,通过Bill模糊查询
	@Override
	public List<Bill> getPageBillList(Connection connection, Bill bill,
			PageUtil pageUtil) throws Exception {
		List<Bill> pageBillList = new ArrayList<Bill>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		if (connection != null) {
			// 拼接sql语句和params
			StringBuffer sql = new StringBuffer();
			sql.append("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id ");
			List<Object> list = new ArrayList<Object>();

			if (!StringUtils.isNullOrEmpty(bill.getProductName())) {
				sql.append(" and b.productName like ? ");
				list.add("%" + bill.getProductName() + "%");
			}
			if (bill.getProviderId() != null) {
				sql.append(" and b.providerId=? ");
				list.add(bill.getProviderId());
			}
			if (bill.getIsPayment() != null) {
				sql.append(" and b.isPayment=? ");
				list.add(bill.getIsPayment());
			}

			sql.append(" order by creationDate desc limit ?,?");
			list.add((pageUtil.getPageIndex() - 1) * pageUtil.getPageSize());
			list.add(pageUtil.getPageSize());

			Object[] params = list.toArray();

			System.out.println(params);

			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
			while (rs.next()) {
				Bill _bill = new Bill();
				_bill.setId(rs.getInt("id"));
				_bill.setBillCode(rs.getString("billCode"));
				_bill.setProductName(rs.getString("productName"));
				_bill.setProductDesc(rs.getString("productDesc"));
				_bill.setProductUnit(rs.getString("productUnit"));
				_bill.setProductCount(rs.getBigDecimal("productCount"));
				_bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
				_bill.setIsPayment(rs.getInt("isPayment"));
				_bill.setProviderId(rs.getInt("providerId"));
				_bill.setProviderName(rs.getString("providerName"));
				_bill.setCreationDate(rs.getTimestamp("creationDate"));
				_bill.setCreatedBy(rs.getInt("createdBy"));
				pageBillList.add(_bill);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return pageBillList;
	}

	// 获取订单条数，模糊查询
	@Override
	public Integer getTotalCount(Connection connection, Bill bill)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Integer billCount = 0;

		if (connection != null) {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(1) from smbms_bill b, smbms_provider p where b.providerId = p.id ");
			List<Object> list = new ArrayList<Object>();
			if (!StringUtils.isNullOrEmpty(bill.getProductName())) {
				sql.append(" and b.productName like ? ");
				list.add("%" + bill.getProductName() + "%");
			}

			System.out.println(bill.getProviderId());
			if (bill.getProviderId() != null) {
				sql.append(" and b.providerId=? ");
				list.add(bill.getProviderId());
			}
			if (bill.getIsPayment() != null) {
				sql.append(" and b.isPayment=? ");
				list.add(bill.getIsPayment());
			}
			Object[] params = list.toArray();
			rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
			if (rs.next()) {
				billCount = rs.getInt(1);
			}
			BaseDao.closeResource(null, pstm, rs);
		}
		return billCount;
	}
}
