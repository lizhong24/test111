package cn.smbms.service.bill.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import cn.smbms.bean.Bill;
import cn.smbms.dao.DaoFactory;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.service.bill.BillService;
import cn.smbms.util.BaseDao;
import cn.smbms.util.PageUtil;

/**
 * 账单的业务逻辑层
 */
public class BillServiceImpl implements BillService {

	private BillDao billDao;

	public BillServiceImpl() {
		billDao = (BillDao) DaoFactory.getDaoImpl("BillDao");
	}

	// 通过id得到账单对象
	@Override
	public Bill getById(Serializable id) {
		Bill bill = null;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			bill = billDao.getById(connection, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return bill;
	}

	// 增加账单
	@Override
	public boolean add(Bill bill) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = billDao.add(connection, bill);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 修改账单
	@Override
	public boolean update(Bill bill) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = billDao.modify(connection, bill);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 删除账单
	@Override
	public boolean delBill(String id) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			flag = billDao.deleteById(connection, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	// 根据供应商id得到账单数（本方法没用，可以干掉！！！！）
	@Override
	public int getBillCountByProId(String proId) {
		int billCount = 0;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			billCount = billDao.getBillCountByProId(connection, proId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return billCount;
	}

	// 根据账单模糊查询得到分页后的账单列表
	@Override
	public List<Bill> getPageBillList(Bill bill, PageUtil pageUtil) {
		List<Bill> pageBillList = null;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			pageBillList = billDao.getPageBillList(connection, bill, pageUtil);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return pageBillList;
	}

	// 获取订单总条数，模糊查询
	@Override
	public int getTotalCount(Bill bill) {
		int totalCounts = 0;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			totalCounts = billDao.getTotalCount(connection, bill);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeResource(connection, null, null);
		}
		return totalCounts;
	}

}
