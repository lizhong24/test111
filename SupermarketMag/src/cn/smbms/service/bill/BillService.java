package cn.smbms.service.bill;

import java.util.List;

import cn.smbms.bean.Bill;
import cn.smbms.service.ConService;
import cn.smbms.util.PageUtil;

public interface BillService extends ConService<Bill> {

	/**
	 * 通过id，删除订单
	 * @param id
	 * @return
	 */
	public boolean delBill(String id);

	/**
	 * 根据供应商Id，查询订单数量
	 * @param proId
	 * @return
	 */
	public int getBillCountByProId(String proId);

	/**
	 * 获取分页BillList,模糊查询
	 * @param bill
	 * @param pageUtil
	 * @return
	 */
	public List<Bill> getPageBillList(Bill bill, PageUtil pageUtil);

	/**
	 * 获取订单条数，模糊查询
	 * @param bill
	 * @return
	 */
	public int getTotalCount(Bill bill);
}
