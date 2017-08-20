package cn.smbms.dao.bill;

import java.sql.Connection;
import java.util.List;

import cn.smbms.bean.Bill;
import cn.smbms.dao.ConDao;
import cn.smbms.util.PageUtil;

public interface BillDao extends ConDao<Bill> {

	/**
	 * 根据供应商Id，查询订单数量
	 * @param connection
	 * @param proId
	 * @return
	 */
	public int getBillCountByProId(Connection connection, String proId)
			throws Exception;

	/**
	 * 分页获取pageBillList,通过Bill模糊查询
	 * @param connection
	 * @param bill
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List<Bill> getPageBillList(Connection connection, Bill bill,
			PageUtil pageUtil) throws Exception;

	/**
	 * 获取订单条数，模糊查询
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCount(Connection connection, Bill bill)
			throws Exception;

}
